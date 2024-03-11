package com.example.myapplication.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.GreetingPreview
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GreetingPreviewUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `when I test, then it works`() {
        // Arrange or setup
        composeTestRule.setContent {
            GreetingPreview()
        }

        // Assertion
        composeTestRule.onNodeWithText("Hello Android!")
    }
}
