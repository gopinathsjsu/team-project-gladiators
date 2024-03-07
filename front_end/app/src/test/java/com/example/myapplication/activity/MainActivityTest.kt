package com.example.myapplication.activity

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test AuthTokenActivity`() {
        launchActivity<MainActivity>().use { scenario ->
            scenario.onActivity { activity ->
                composeTestRule.onNodeWithText("Hello Android!")
            }
        }
    }
}
