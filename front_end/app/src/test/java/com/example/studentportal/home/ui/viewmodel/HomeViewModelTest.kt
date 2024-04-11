package com.example.studentportal.home.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.home.usecase.StudentUseCase
import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import com.example.studentportal.notifications.usecase.NotificationListUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private val mainDispatcher = StandardTestDispatcher()

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @Before
    fun before() {
        mockkConstructor(StudentUseCase::class)
    }

    @After
    fun tearDown() {
        unmockkConstructor(StudentUseCase::class)
        stopKoin()
    }

    @Test
    fun `test viewModel student use case init`() {
        val viewModel = HomeViewModel.StudentViewModelFactory.create(
            HomeViewModel::class.java,
            mockk(relaxed = true)
        )

        // Check of user type
        assertThat(viewModel.userType).isEqualTo(UserType.STUDENT)
    }

    @Test
    fun `test student fetch loading`() = runTest {
        // Set Up Resources
        coEvery { anyConstructed<StudentUseCase>().launch() } returns successFlow(
            StudentUseCaseModel(
                id = "Id",
                name = "Name",
                email = "email"
            )
        )
        val viewModel = HomeViewModel.StudentViewModelFactory.create(
            HomeViewModel::class.java,
            mockk(relaxed = true)
        )

        // Act
        viewModel.fetchStudent("Id")

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.isLoading()).isTrue()
    }

    @Test
    fun `test student fetch success`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<StudentUseCase>().launch() } returns successFlow(
            StudentUseCaseModel(
                id = "Id",
                name = "Name",
                email = "email"
            )
        )
        val viewModel = HomeViewModel(
            UserType.STUDENT,
            mainDispatcher
        )

        // Act
        viewModel.fetchStudent("Id")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.data()).isEqualTo(
            UserUiModel(
                id = "Id",
                name = "Name",
                email = "email",
                type = UserType.STUDENT
            )
        )
    }

    @Test
    fun `test student fetch error`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<StudentUseCase>().launch() } returns defaultFailureFlow()
        val viewModel = HomeViewModel(
            UserType.STUDENT,
            mainDispatcher
        )

        // Act
        viewModel.fetchStudent("Id")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.error()).isEqualTo(
            DefaultError("Parse error")
        )
    }
}
