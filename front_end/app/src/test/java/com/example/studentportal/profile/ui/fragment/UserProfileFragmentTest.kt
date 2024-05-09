package com.example.studentportal.profile.ui.fragment

import android.content.SharedPreferences
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.ui.viewModel.UserProfileViewModel
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class UserProfileFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var mockRepo: UserRepository

    private val mainDispatcher = StandardTestDispatcher()

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
                    single {
                        mockk<SharedPreferences>(relaxed = true)
                    }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test update user profile`() = runTest(mainDispatcher) {
        val expectedResult = UserUseCaseModel(
            id = "id",
            password = "password",
            biography = "biography",
            email = "email",
            phone = "phone",
            firstName = "firstName",
            lastName = "lastName",
            type = UserType.FACULTY.name
        )
        coEvery { mockRepo.updateUser(any()) } returns Response.success(
            expectedResult
        )
        launchFragmentInContainer<ProfileFragment>(
            fragmentArgs = bundleOf(
                ProfileFragment.KEY_USER_TYPE to UserType.FACULTY.name,
                ProfileFragment.KEY_USER_ID to "userId"
            ),
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ProfileFragment(
                        viewModelFactory {
                            initializer {
                                UserProfileViewModel(mainDispatcher)
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
            composeTestRule.onNodeWithTag("editProfile").performClick()
            fragment.childFragmentManager.executePendingTransactions()

            assertThat(
                fragment.childFragmentManager.fragments.any {
                    it is EditProfileFragment
                }
            ).isTrue()

            // Input Course Data
            composeTestRule.onNodeWithTag("biographyInput").performTextInput("CourseName")
            composeTestRule.onNodeWithTag("phoneInput").performTextInput("Link")
            composeTestRule.onNodeWithTag("emailInput").performTextInput("Wrong Date")

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
                mockRepo.updateUser(any())
            }
        }
    }

    @Ignore("FLAKY")
    @Test
    fun `test initial setup`() {
        launchFragmentInContainer<ProfileFragment>().onFragment { fragment ->
            // Test Loading State
            fragment.viewModel._uiResultLiveData.postValue(BaseUiState.Loading())
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()

            // Test Success State - Empty State
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    UserUiModel(
                        id = "id",
                        password = "password",
                        biography = "biography",
                        email = "email",
                        phone = "phone",
                        firstName = "firstName",
                        lastName = "lastName"
                    )
                )
            )
            composeTestRule.apply {
                onNodeWithText("firstName lastName").assertIsDisplayed()
                onNodeWithText("email").assertIsDisplayed()
                onNodeWithText("biography").assertIsDisplayed()
            }
        }
    }

    @Ignore("FLAKY")
    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: ProfileFragment? = null
        launchFragmentInContainer<ProfileFragment>().onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
