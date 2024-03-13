package com.example.studentportal.home.ui.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiResult
import com.example.studentportal.common.usecase.DefaultUseCaseError
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test fetch student loading`() {
        ActivityScenario.launch(HomeActivity::class.java)
            .use { scenario ->
                scenario.onActivity {
                    composeTestRule
                        .onNodeWithText("Loading...").assertIsDisplayed()
                }
            }
    }

    @Test
    fun `test fetch student success`() {
        ActivityScenario.launch(HomeActivity::class.java)
            .use { scenario ->
                scenario.onActivity {
                    it.viewModel._uiResultLiveData.value = BaseUiResult.Success(
                        UserUiModel(
                            id = "Id",
                            name = "Name",
                            email = "email",
                            type = UserType.STUDENT
                        )
                    )
                    composeTestRule
                        .onNodeWithText("Hello Name your email is email").assertIsDisplayed()
                }
            }
    }

    @Test
    fun `test fetch student error`() {
        ActivityScenario.launch(HomeActivity::class.java)
            .use { scenario ->
                scenario.onActivity {
                    it.viewModel._uiResultLiveData.value = BaseUiResult.Error(
                        DefaultUseCaseError("Error Loading User")
                    )
                    composeTestRule
                        .onNodeWithText("Error Loading User").assertIsDisplayed()
                }
            }
    }
}
