package com.example.studentportal.profile.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test initial setup`() {
        launchFragmentInContainer<ProfileFragment>().onFragment {
            composeTestRule.onNodeWithText("PROFILE LAYOUT").assertIsDisplayed()
        }
    }

    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: ProfileFragment? = null
        launchFragmentInContainer<ProfileFragment>().onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
