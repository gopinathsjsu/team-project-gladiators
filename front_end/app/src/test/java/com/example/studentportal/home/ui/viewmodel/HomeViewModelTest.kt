package com.example.studentportal.home.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.usecase.CoursesUseCase
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import io.mockk.unmockkConstructor
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import java.util.Date

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private val mainDispatcher = StandardTestDispatcher()

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @Before
    fun before() {
        mockkConstructor(CoursesUseCase::class)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test student fetch loading`() = runTest {
        // Set Up Resources
        coEvery { anyConstructed<CoursesUseCase>().launch() } returns successFlow(
            CourseListUseCaseModel(
                useCaseModels = listOf(
                    BaseCourseUseCaseModel.SemesterUseCaseModel(
                        id = "semesterId",
                        startDate = Date(),
                        endDate = Date(),
                        name = "Semester"
                    ),
                    BaseCourseUseCaseModel.CourseUseCaseModel(
                        id = "courseId",
                        instructor = "instructor",
                        enrolledStudents = setOf(),
                        assignments = setOf(),
                        semester = "semester",
                        published = false,
                        name = "CourseName",
                        description = "Description"
                    )
                )
            )
        )
        val viewModel = HomeViewModel.HomeViewModelFactory.create(
            HomeViewModel::class.java,
            mockk(relaxed = true)
        )

        // Act
        viewModel.fetchCourses("Id")

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.isLoading()).isTrue()
    }

    @Test
    fun `test student fetch success`() = runTest(mainDispatcher) {
        val date = Date()
        // Set Up Resources
        coEvery { anyConstructed<CoursesUseCase>().launch() } returns successFlow(
            CourseListUseCaseModel(
                useCaseModels = listOf(
                    BaseCourseUseCaseModel.SemesterUseCaseModel(
                        id = "semesterId",
                        startDate = date,
                        endDate = date,
                        name = "Semester"
                    ),
                    BaseCourseUseCaseModel.CourseUseCaseModel(
                        id = "courseId",
                        instructor = "instructor",
                        enrolledStudents = setOf(),
                        assignments = setOf(),
                        semester = "semester",
                        published = false,
                        name = "CourseName",
                        description = "Description"
                    )
                )
            )
        )
        val viewModel = HomeViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchCourses("Id")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        val models = viewModel.uiResultLiveData.value?.data()
        assertThat(models?.uiModels?.size).isEqualTo(2)
        assertThat(models?.uiModels?.get(0)).isEqualTo(
            BaseCourseUiModel.SemesterUiModel(
                id = "semesterId",
                startDate = date,
                endDate = date,
                name = "Semester"
            )
        )
        assertThat(models?.uiModels?.get(1)).isEqualTo(
            BaseCourseUiModel.CourseUiModel(
                id = "courseId",
                instructor = "instructor",
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = "semester",
                isPublished = false,
                name = "CourseName",
                description = "Description"
            )
        )
    }

    @Test
    fun `test student fetch error`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<CoursesUseCase>().launch() } returns defaultFailureFlow()
        val viewModel = HomeViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchCourses("Id")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.error()).isEqualTo(
            DefaultError("Parse error")
        )
    }

    companion object {

        @AfterClass
        @JvmStatic
        fun afterClass(){
            unmockkAll()
        }
    }
}
