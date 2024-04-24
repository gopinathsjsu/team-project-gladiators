package com.example.studentportal.notifications.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.notifications.usecase.NotificationListUseCase
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class NotificationListViewModelTest {
    private val mainDispatcher = StandardTestDispatcher()

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @Before
    fun before() {
        mockkConstructor(NotificationListUseCase::class)
    }

    @After
    fun tearDown() {
        unmockkConstructor(NotificationListUseCase::class)
        stopKoin()
    }

    @Test
    fun `test notifications fetch loading`() = runTest {
        // Set Up Resources
        val viewModel = NotificationListViewModel.NotificationListViewModelFactory.create(
            NotificationListViewModel::class.java,
            mockk(relaxed = true)
        )

        // Act
        viewModel.fetchNotifications()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.isLoading()).isTrue()
    }

    @Ignore("FLAKY")
    @Test
    fun `test notifications fetch error`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<NotificationListUseCase>().launch() } returns defaultFailureFlow()
        val viewModel = NotificationListViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchNotifications()
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.error()).isEqualTo(
            DefaultError("Parse error")
        )
    }

    @Test
    fun `test notifications fetch success`() = runTest(mainDispatcher) {
        val useCaseModel = NotificationListUseCaseModel(listOf())
        // Set Up Resources
        coEvery { anyConstructed<NotificationListUseCase>().launch() } returns successFlow(
            useCaseModel
        )
        val viewModel = NotificationListViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchNotifications()
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.data()).isEqualTo(
            useCaseModel.toUiModel()
        )
    }
}
