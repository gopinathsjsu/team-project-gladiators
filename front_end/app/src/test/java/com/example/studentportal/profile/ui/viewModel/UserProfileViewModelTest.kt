package com.example.studentportal.profile.ui.viewModel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.MainDispatcherTestRule
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.profile.usecase.UserProfileUseCase
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UserProfileViewModelTest {
    private val mainDispatcher = StandardTestDispatcher()

    @get:Rule
    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test user fetch loading`() = runTest {
        // Set Up Resources
        val viewModel = UserProfileViewModel.UserProfileViewModelFactory.create(
            UserProfileViewModel::class.java,
            mockk(relaxed = true)
        )

        // Act
        viewModel.fetchUserData("id")

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value?.isLoading()).isTrue()
    }

    @Test
    fun `test user fetch error`() = runTest(mainDispatcher) {
        // Set Up Resources
        coEvery { anyConstructed<UserProfileUseCase>().launch() } returns defaultFailureFlow()
        val viewModel = UserProfileViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchUserData("userId")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.error()).isEqualTo(
            DefaultError("Parse error")
        )
    }

    @Test
    fun `test user fetch success`() = runTest(mainDispatcher) {
        val useCaseModel = UserUseCaseModel(
            id = "id",
            password = "password",
            biography = "biography",
            email = "email",
            phone = "phone",
            firstName = "firstName",
            lastName = "lastName"
        )
        // Set Up Resources
        coEvery { anyConstructed<UserProfileUseCase>().launch() } returns successFlow(
            useCaseModel
        )
        val viewModel = UserProfileViewModel(
            mainDispatcher
        )

        // Act
        viewModel.fetchUserData("id")
        mainDispatcher.scheduler.advanceUntilIdle()

        // Verify Success Result
        assertThat(viewModel.uiResultLiveData.value.data()).isEqualTo(
            useCaseModel.toUiModel()
        )
    }

    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            mockkConstructor(UserProfileUseCase::class)
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            unmockkConstructor(UserProfileUseCase::class)
        }
    }
}
