package com.example.studentportal.grades.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.grades.usecase.GradeListUseCase
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GradeListViewModelTest {
    private val mainDispatcher = StandardTestDispatcher()

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @Before
    fun before() {
        mockkConstructor(GradeListUseCase::class)
    }

    @After
    fun tearDown() {
        unmockkConstructor(GradeListUseCase::class)
        stopKoin()
    }

    @Test
    fun `test grade fetch success`() = runTest(mainDispatcher) {
        val useCaseModel = GradeListUseCaseModel(listOf())
        // Set Up Resources
        coEvery { anyConstructed<GradeListUseCase>().launch() } returns successFlow(
            useCaseModel
        )
        val viewModel = GradeListViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchGrades(assignmentId = "", userId = "")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.data()).isEqualTo(
            useCaseModel.toUiModel()
        )
    }

    @Test
    fun `test grade fetch error`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<GradeListUseCase>().launch() } returns defaultFailureFlow()
        val viewModel = GradeListViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchGrades(assignmentId = "", userId = "")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.error()).isEqualTo(
            DefaultError("Parse error")
        )
    }
}
