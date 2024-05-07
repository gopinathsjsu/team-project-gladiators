package com.example.studentportal.course.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.ui.model.isSuccess
import com.example.studentportal.course.ui.model.SemesterUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseContentViewModel
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel
import com.example.studentportal.course.usecase.model.CourseDetailsUseCaseModel
import com.example.studentportal.home.service.repository.CourseRepository
import com.example.studentportal.home.ui.fragment.HomeFragment
import com.example.studentportal.home.ui.layout.KEY_USER_ID
import com.example.studentportal.home.ui.layout.KEY_USER_TYPE
import com.example.studentportal.home.ui.model.CourseListUiModel
import com.example.studentportal.home.ui.viewmodel.HomeViewModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
import com.example.studentportal.profile.ui.model.UserUiModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response
import java.util.Date

@RunWith(AndroidJUnit4::class)
class CourseInputFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var mockRepo: CourseRepository

    private val mainDispatcher = StandardTestDispatcher()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Before
    fun setUp() {
        mockRepo = mockk(relaxed = true)
        stopKoin()
        startKoin {
            modules(
                module {
                    single {
                        mockRepo
                    }
                }
            )
        }
    }

    @Test
    fun `test open new course window`() = runTest(mainDispatcher) {
        val expectedResult = CourseListUseCaseModel(
            useCaseModels = listOf(
                BaseCourseUseCaseModel.CourseUseCaseModel(
                    id = "id",
                    instructor = "instructor",
                    enrolledStudents = setOf(),
                    assignments = setOf(),
                    semester = "semester",
                    published = false,
                    name = "name",
                    description = "description"
                )
            )
        )
        coEvery { mockRepo.createCourse(any()) } returns Response.success(
            expectedResult
        )
        launchFragmentInContainer<HomeFragment>(
            fragmentArgs = bundleOf(
                KEY_USER_ID to "userId",
                KEY_USER_TYPE to UserType.ADMIN.name
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return HomeFragment(
                        viewModelFactory {
                            initializer {
                                HomeViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                CourseListUiModel(listOf())
            )
            // Check list is empty
            composeTestRule.onNodeWithText("No courses to show").assertIsDisplayed()

            // Perform Click on Add button
            composeTestRule.onNodeWithTag("addCourse").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is CourseInputFragment
                }
            ).isTrue()

            val inputFragment =
                fragment.childFragmentManager.fragments.first { it is CourseInputFragment } as CourseInputFragment

            // Assert Button is Disabled
            composeTestRule.onNodeWithTag("submitButton").assertIsNotEnabled()

            // Input Course Data
            composeTestRule.onNodeWithTag("nameInput").performTextInput("CourseName")
            composeTestRule.onNodeWithTag("descriptionInput").performTextInput("Description")
            inputFragment.viewModel.updateSelectedInstructor(
                UserUiModel(
                    id = "id",
                    password = "password",
                    biography = "biography",
                    email = "email",
                    phone = "phone",
                    firstName = "firstName",
                    lastName = "lastName",
                    type = UserType.FACULTY
                )
            )
            inputFragment.viewModel.updateSelectedSemester(
                SemesterUiModel(
                    id = "ID",
                    name = "NAME",
                    startDate = Date(),
                    endDate = Date()
                )
            )

            // Assert Button is Enabled after valid date
            composeTestRule.onNodeWithTag("submitButton").assertIsEnabled()

            // Submit click
            composeTestRule.onNodeWithTag("submitButton").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            // Verify Dialog Fragment is no longer visible
            assertThat(fragment.childFragmentManager.fragments).isEmpty()

            // Verify loading state
            assertThat(fragment.viewModel.uiResultLiveData.value?.isLoading()).isTrue()

            // Proceed with Api Call
            mainDispatcher.scheduler.advanceUntilIdle()

            // Verify assignment values
            coVerify {
                mockRepo.createCourse(
                    course = match {
                        it.name == "CourseName" && it.description == "Description"
                    }
                )
            }

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isTrue()

            // Verify data contents
            val data = fragment.viewModel._uiResultLiveData.value.data()
            assertThat(data?.uiModels?.size).isEqualTo(1)
            assertThat(data?.uiModels?.get(0))
                .isEqualTo(expectedResult.toUiModel().uiModels.get(0))
        }
    }

    @Test
    fun `test update course window`() = runTest(mainDispatcher) {
        val expectedResult = CourseDetailsUseCaseModel(
            course = BaseCourseUseCaseModel.CourseUseCaseModel(
                id = "id",
                instructor = "instructor",
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = "semester",
                published = false,
                name = "name",
                description = "description"
            ),
            instructor = BaseCourseUseCaseModel.FacultyUseCaseModel(
                id = "id",
                firstName = "firstName",
                lastName = "lastName",
                password = "password"
            )
        )
        coEvery { mockRepo.updateCourse(any()) } returns Response.success(
            expectedResult
        )
        launchFragmentInContainer<CourseFragment>(
            fragmentArgs = bundleOf(
                CourseFragment.KEY_USER_ID to "userId",
                CourseFragment.KEY_USER_TYPE to UserType.ADMIN.name,
                CourseFragment.KEY_COURSE to BaseCourseUseCaseModel.CourseUseCaseModel(
                    id = "id",
                    instructor = "instructor",
                    enrolledStudents = setOf(),
                    assignments = setOf(),
                    semester = "semester",
                    published = false,
                    name = "name",
                    description = "description"
                ).toUiModel()
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return CourseFragment(
                        viewModelFactory {
                            initializer {
                                CourseDetailsViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                expectedResult.toUiModel()
            )

            // Perform Click on Add button
            composeTestRule.onNodeWithTag("editCourse").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is CourseInputFragment
                }
            ).isTrue()

            val inputFragment =
                fragment.childFragmentManager.fragments.first { it is CourseInputFragment } as CourseInputFragment

            // Input Course Data
            composeTestRule.onNodeWithTag("nameInput").assertIsNotEnabled()
            composeTestRule.onNodeWithTag("descriptionInput").assertIsNotEnabled()
            inputFragment.viewModel.updateSelectedInstructor(
                UserUiModel(
                    id = "id",
                    password = "password",
                    biography = "biography",
                    email = "email",
                    phone = "phone",
                    firstName = "firstName",
                    lastName = "lastName",
                    type = UserType.FACULTY
                )
            )
            inputFragment.viewModel.updateSelectedSemester(
                SemesterUiModel(
                    id = "ID",
                    name = "NAME",
                    startDate = Date(),
                    endDate = Date()
                )
            )

            // Submit click
            composeTestRule.onNodeWithTag("submitButton").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            // Verify Dialog Fragment is no longer visible
            assertThat(fragment.childFragmentManager.fragments).isEmpty()

            // Verify loading state
            assertThat(fragment.viewModel.uiResultLiveData.value?.isLoading()).isTrue()

            // Proceed with Api Call
            mainDispatcher.scheduler.advanceUntilIdle()

            // Verify assignment values
            coVerify {
                mockRepo.updateCourse(
                    course = match {
                        it.name == "name" && it.description == "description"
                    }
                )
            }

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isTrue()

            // Verify data contents
            val data = fragment.viewModel._uiResultLiveData.value.data()
            assertThat(data?.courseUiModel).isEqualTo(expectedResult.toUiModel().courseUiModel)
            assertThat(data?.instructorUiModel)
                .isEqualTo(expectedResult.toUiModel().instructorUiModel)
        }
    }

    @Test
    fun `test update course content`() = runTest(mainDispatcher) {
        val expectedResult = CourseDetailsUseCaseModel(
            course = BaseCourseUseCaseModel.CourseUseCaseModel(
                id = "id",
                instructor = "instructor",
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = "semester",
                published = false,
                name = "name",
                description = "description"
            ),
            instructor = BaseCourseUseCaseModel.FacultyUseCaseModel(
                id = "id",
                firstName = "firstName",
                lastName = "lastName",
                password = "password"
            )
        )
        coEvery { mockRepo.updateCourse(any()) } returns Response.success(
            expectedResult
        )
        coEvery { mockRepo.fetchCourseDetails(any()) } returns Response.success(
            expectedResult
        )
        val viewModel = CourseContentViewModel(mainDispatcher)
        launchFragmentInContainer<CourseContentFragment>(
            fragmentArgs = bundleOf(
                CourseContentFragment.KEY_COURSE_ID to "courseId",
                CourseContentFragment.KEY_USER_TYPE to UserType.FACULTY.name,
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return CourseContentFragment(
                        viewModelFactory {
                            initializer {
                               viewModel
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                expectedResult.toUiModel()
            )
            composeTestRule.onNodeWithTag("editContent").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is UpdateCourseContentFragment
                }
            ).isTrue()

            composeTestRule.onNodeWithTag("contentInput").performTextInput("Updated Content")
            composeTestRule.onNodeWithTag("submitButton").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            // Verify Dialog Fragment is no longer visible
            assertThat(fragment.childFragmentManager.fragments).isEmpty()

            // Proceed with Api Call
            mainDispatcher.scheduler.advanceUntilIdle()

            // Verify assignment values
            coVerify {
                mockRepo.updateCourse(
                    course = any()
                )
            }
        }
    }
}
