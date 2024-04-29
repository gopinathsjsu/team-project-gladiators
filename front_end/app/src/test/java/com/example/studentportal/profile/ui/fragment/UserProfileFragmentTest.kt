package com.example.studentportal.profile.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.profile.ui.model.UserUiModel
import org.junit.After
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class UserProfileFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
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
