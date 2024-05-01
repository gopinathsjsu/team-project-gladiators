package com.example.studentportal.grades.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.grades.ui.model.GradeListUiModel
import com.example.studentportal.grades.ui.model.GradeUiModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class GradesFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test initial setup`() {
        launchFragmentInContainer<GradesFragment>(
            fragmentArgs = bundleOf(
                GradesFragment.KEY_ASSIGNMENT_ID to "assignmentId",
                GradesFragment.KEY_USER_ID to "userId"
            )
        ).onFragment { fragment ->
            // Test Loading State
            fragment.viewModel._uiResultLiveData.postValue(BaseUiState.Loading())
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()

            // Test Success State - Empty State
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    GradeListUiModel(
                        listOf()
                    )
                )
            )
            composeTestRule.onNodeWithText("No grades to show").assertIsDisplayed()

            // Test Success State - Multiple items
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    GradeListUiModel(
                        listOf(
                            GradeUiModel(
                                gradeId = "1",
                                score = 10,
                                studentFirstName = "First-N1",
                                studentLastName = "Last-N1",
                                studentId = "1"
                            ),
                            GradeUiModel(
                                gradeId = "2",
                                score = 20,
                                studentFirstName = "First-N2",
                                studentLastName = "Last-N2",
                                studentId = "2"
                            ),
                            GradeUiModel(
                                gradeId = "3",
                                score = 30,
                                studentFirstName = "First-N3",
                                studentLastName = "Last-N3",
                                studentId = "3"
                            )
                        )
                    )
                )
            )
            composeTestRule.apply {
                onNodeWithText("First-N1 Last-N1").assertIsDisplayed()
                onNodeWithText("First-N3 Last-N3").assertIsDisplayed()
                onNodeWithText("30/100").assertIsDisplayed()
            }
        }
    }

    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: GradesFragment? = null
        launchFragmentInContainer<GradesFragment>(
            fragmentArgs = bundleOf(
                GradesFragment.KEY_ASSIGNMENT_ID to "assignmentId",
                GradesFragment.KEY_USER_ID to "userId"
            )
        ).onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
