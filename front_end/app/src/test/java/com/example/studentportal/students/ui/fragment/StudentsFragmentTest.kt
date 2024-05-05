package com.example.studentportal.students.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.students.ui.model.StudentListUiModel
import com.example.studentportal.students.ui.model.StudentUiModel
import org.junit.After
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class StudentsFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Ignore("FLAKY")
    @Test
    fun `test initial setup`() {
        launchFragmentInContainer<StudentsFragment>().onFragment { fragment ->
            // Test Loading State
            fragment.viewModel._uiResultLiveData.postValue(BaseUiState.Loading())
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()

            // Test Success State - Empty State
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    StudentListUiModel(
                        listOf()
                    )
                )
            )
            composeTestRule.onNodeWithText("No students found").assertIsDisplayed()

            // Test Success State - Multiple items
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    StudentListUiModel(
                        listOf(
                            StudentUiModel(
                                id = "1",
                                firstName = "John",
                                lastName = "Doe",
                                email = "john.doe@example.com",
                                phone = "555-1234",
                                type = UserType.STUDENT,
                                biography = "A brief bio of John."
                            ),
                            StudentUiModel(
                                id = "2",
                                firstName = "Jane",
                                lastName = "Smith",
                                email = "jane.smith@example.com",
                                phone = "555-4321",
                                type = UserType.STUDENT,
                                biography = "A brief bio of Jane."
                            )
                        )
                    )
                )
            )
            composeTestRule.apply {
                onNodeWithText("John Doe").assertIsDisplayed()
                onNodeWithText("Jane Smith").assertIsDisplayed()
                onNodeWithText("Graduate").assertIsDisplayed()
                onNodeWithText("Undergraduate").assertIsDisplayed()
            }
        }
    }

    @Ignore("FLAKY")
    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: StudentsFragment? = null
        launchFragmentInContainer<StudentsFragment>().onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
