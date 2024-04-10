package com.example.studentportal.home.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test fetch student loading`() {
        launchFragmentInContainer<HomeFragment>().onFragment {
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
        }
    }

    @Test
    fun `test fetch student success`() {
        launchFragmentInContainer<HomeFragment>().onFragment { homeFragment ->
            homeFragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                UserUiModel(
                    id = "Id",
                    name = "Name",
                    email = "email",
                    type = UserType.STUDENT
                )
            )
            composeTestRule.onNodeWithText(
                text = "Hello Name your email is email"
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `test fetch student error`() {
        launchFragmentInContainer<HomeFragment>().onFragment { homeFragment ->
            homeFragment.viewModel._uiResultLiveData.value = BaseUiState.Error(
                DefaultError("Error Loading User")
            )
            composeTestRule.onNodeWithText("Error Loading User").assertIsDisplayed()
        }
    }

    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: HomeFragment? = null
        launchFragmentInContainer<HomeFragment>().onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
