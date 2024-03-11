package com.example.studentportal.activity

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.home.ui.activity.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test AuthTokenActivity`() {
        launchActivity<HomeActivity>().use { scenario ->
            scenario.onActivity { activity ->
                composeTestRule.onNodeWithText("Hello Android!")
            }
        }
    }
}
