package com.example.studentportal.assignment.ui

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
import com.example.studentportal.assignment.service.repository.AssignmentRepository
import com.example.studentportal.assignment.ui.fragment.AddAssignmentFragment
import com.example.studentportal.assignment.ui.fragment.AssignmentsFragment
import com.example.studentportal.assignment.ui.model.AssignmentListUiModel
import com.example.studentportal.assignment.ui.viewmodel.AssignmentsViewModel
import com.example.studentportal.assignment.usecase.models.AssignmentListUseCaseModel
import com.example.studentportal.assignment.usecase.models.AssignmentUseCaseModel
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.ui.model.isSuccess
import com.example.studentportal.course.ui.model.UserType
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response
import java.util.Date

@RunWith(AndroidJUnit4::class)
class AssignmentListFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var mockRepo: AssignmentRepository

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
    fun `test open new assignment window`() = runTest(mainDispatcher) {
        val expectedResult = AssignmentListUseCaseModel(
            listOf(
                AssignmentUseCaseModel(
                    id = "id",
                    dueDate = Date(),
                    name = "Name",
                    submissions = setOf(),
                    course = "course",
                    link = "link"
                )
            )
        )
        coEvery { mockRepo.createNewAssignment(any()) } returns Response.success(
            expectedResult
        )
        launchFragmentInContainer<AssignmentsFragment>(
            fragmentArgs = bundleOf(
                AssignmentsFragment.KEY_COURSE_ID to "courseId",
                AssignmentsFragment.KEY_USER_TYPE to UserType.FACULTY.name
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return AssignmentsFragment(
                        viewModelFactory {
                            initializer {
                                AssignmentsViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                AssignmentListUiModel(listOf())
            )
            // Check list is empty
            composeTestRule.onNodeWithText("No assignments have been posted for this course").assertIsDisplayed()

            // Perform Click on Add button
            composeTestRule.onNodeWithTag("addAssignment").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is AddAssignmentFragment
                }
            ).isTrue()

            // Assert Button is Disabled
            composeTestRule.onNodeWithTag("submitButton").assertIsNotEnabled()

            // Input Course Data
            composeTestRule.onNodeWithTag("nameInput").performTextInput("CourseName")
            composeTestRule.onNodeWithTag("linkInput").performTextInput("Link")
            composeTestRule.onNodeWithTag("dateInput").performTextInput("Wrong Date")

            // Assert Button is Disabled
            composeTestRule.onNodeWithTag("submitButton").assertIsNotEnabled()

            // Assert
            composeTestRule.onNodeWithTag("dateInput").performTextInput("04292024")

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
                mockRepo.createNewAssignment(
                    assignment = match {
                        it.course == "courseId" &&
                            it.link == "Link" &&
                            it.name == "CourseName"
                    }
                )
            }

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isTrue()

            // Verify data contents
            val data = fragment.viewModel._uiResultLiveData.value.data()
            assertThat(data?.uiModels?.size).isEqualTo(1)
            assertThat(data?.uiModels?.get(0)).isEqualTo(expectedResult.toUiModel().uiModels.get(0))
        }
    }

    @Test
    fun `test open new assignment window, error when creating`() = runTest(mainDispatcher) {
        coEvery { mockRepo.createNewAssignment(any()) } returns Response.error(
            400,
            mockk(relaxed = true) {
                every { string() } returns "{\"message\":\"Backend Error\" }"
            }
        )
        launchFragmentInContainer<AssignmentsFragment>(
            fragmentArgs = bundleOf(
                AssignmentsFragment.KEY_COURSE_ID to "courseId",
                AssignmentsFragment.KEY_USER_TYPE to UserType.FACULTY.name
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return AssignmentsFragment(
                        viewModelFactory {
                            initializer {
                                AssignmentsViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                AssignmentListUiModel(listOf())
            )
            // Check list is empty
            composeTestRule.onNodeWithText("No assignments have been posted for this course").assertIsDisplayed()

            // Perform Click on Add button
            composeTestRule.onNodeWithTag("addAssignment").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is AddAssignmentFragment
                }
            ).isTrue()

            // Assert Button is Disabled
            composeTestRule.onNodeWithTag("submitButton").assertIsNotEnabled()

            // Input Course Data
            composeTestRule.onNodeWithTag("nameInput").performTextInput("CourseName")
            composeTestRule.onNodeWithTag("linkInput").performTextInput("Link")
            composeTestRule.onNodeWithTag("dateInput").performTextInput("Wrong Date")

            // Assert Button is Disabled
            composeTestRule.onNodeWithTag("submitButton").assertIsNotEnabled()

            // Assert
            composeTestRule.onNodeWithTag("dateInput").performTextInput("04292024")

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
                mockRepo.createNewAssignment(
                    assignment = match {
                        it.course == "courseId" &&
                            it.link == "Link" &&
                            it.name == "CourseName"
                    }
                )
            }

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isTrue()

            // Verify data contents
            val data = fragment.viewModel._uiResultLiveData.value.data()
            assertThat(data?.uiModels?.size).isEqualTo(0)
        }
    }

    @Test
    fun `test assignmentList`() = runTest(mainDispatcher) {
        val expectedResult = AssignmentListUseCaseModel(
            listOf(
                AssignmentUseCaseModel(
                    id = "id",
                    dueDate = Date(),
                    name = "Name",
                    submissions = setOf(),
                    course = "course",
                    link = "link"
                )
            )
        )
        coEvery { mockRepo.fetchAssignments(any()) } returns Response.success(
            expectedResult
        )
        launchFragmentInContainer<AssignmentsFragment>(
            fragmentArgs = bundleOf(
                AssignmentsFragment.KEY_COURSE_ID to "courseId",
                AssignmentsFragment.KEY_USER_TYPE to UserType.FACULTY.name
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return AssignmentsFragment(
                        viewModelFactory {
                            initializer {
                                AssignmentsViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            // Verify loading state
            assertThat(fragment.viewModel.uiResultLiveData.value?.isLoading()).isTrue()

            // Proceed with Api Call
            mainDispatcher.scheduler.advanceUntilIdle()

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isTrue()

            // Verify data contents
            val data = fragment.viewModel._uiResultLiveData.value.data()
            assertThat(data?.uiModels?.size).isEqualTo(1)
            assertThat(data?.uiModels?.get(0)).isEqualTo(expectedResult.toUiModel().uiModels.get(0))
        }
    }

    @Test
    fun `test assignmentList, error`() = runTest(mainDispatcher) {
        val expectedResult = AssignmentListUseCaseModel(
            listOf(
                AssignmentUseCaseModel(
                    id = "id",
                    dueDate = Date(),
                    name = "Name",
                    submissions = setOf(),
                    course = "course",
                    link = "link"
                )
            )
        )
        coEvery { mockRepo.fetchAssignments(any()) } returns Response.error(
            400,
            mockk(relaxed = true) {
                every { string() } returns "{\"message\":\"Backend Error\" }"
            }
        )
        launchFragmentInContainer<AssignmentsFragment>(
            fragmentArgs = bundleOf(
                AssignmentsFragment.KEY_COURSE_ID to "courseId",
                AssignmentsFragment.KEY_USER_TYPE to UserType.FACULTY.name
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return AssignmentsFragment(
                        viewModelFactory {
                            initializer {
                                AssignmentsViewModel(mainDispatcher)
                            }
                        }
                    )
                }
            }
        ).onFragment { fragment ->
            // Verify loading state
            assertThat(fragment.viewModel.uiResultLiveData.value?.isLoading()).isTrue()

            // Proceed with Api Call
            mainDispatcher.scheduler.advanceUntilIdle()

            // Verify view model receives data
            assertThat(fragment.viewModel._uiResultLiveData.value?.isSuccess()).isFalse()

            // Verify data contents
            val error = fragment.viewModel._uiResultLiveData.value.error()
            assertThat(error?.message).isEqualTo("Backend Error")
        }
    }
}
