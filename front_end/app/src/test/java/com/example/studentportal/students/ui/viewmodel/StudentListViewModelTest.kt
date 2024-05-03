package com.example.studentportal.students.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.students.usecase.StudentListUseCase
import com.example.studentportal.students.usecase.model.StudentListUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class StudentListViewModelTest {
    private val mainDispatcher = StandardTestDispatcher()
    private val courseId = "course123"

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @Before
    fun before() {
        mockkConstructor(StudentListUseCase::class)
    }

    @After
    fun tearDown() {
        unmockkConstructor(StudentListUseCase::class)
        stopKoin()
    }

    @Ignore("FLAKY")
    @Test
    fun `test students fetch loading`() = runTest {
        // Set Up Resources
        val viewModel = StudentListViewModel.StudentListViewModelFactory.create(
            StudentListViewModel::class.java,
            mockk(relaxed = true)
        )

        // Act
        viewModel.fetchStudents(courseId)

        // Verify Loading Result
        assertThat(viewModel.uiResultLiveData.value?.isLoading()).isTrue()
    }

    @Test
    fun `test students fetch error`() = runTest {
        val fakeException = Exception("Network Error")
        coEvery { anyConstructed<StudentListUseCase>().launch() } returns defaultFailureFlow(fakeException)

        val viewModel = StudentListViewModel(mainDispatcher)

        viewModel.fetchStudents(courseId)
        advanceUntilIdle()

        assertThat(viewModel.uiResultLiveData.value.error()).isEqualTo(DefaultError("Network Error"))
    }

    @Ignore("FLAKY")
    @Test
    fun `test students fetch success`() = runTest(mainDispatcher) {
        val useCaseModel = StudentListUseCaseModel(listOf())
        // Set Up Resources
        coEvery { anyConstructed<StudentListUseCase>().launch() } returns successFlow(
            useCaseModel
        )
        val viewModel = StudentListViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchStudents(courseId)
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.data()).isEqualTo(
            useCaseModel.toUiModel()
        )
    }
}
