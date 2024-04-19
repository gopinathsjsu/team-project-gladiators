package com.example.studentportal.home.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.ui.model.CourseListUiModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import java.util.Date

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test fetch courses loading`() {
        launchFragmentInContainer<HomeFragment>().onFragment {
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
        }
    }

    @Test
    fun `test fetch course success`() {
        val date = Date()
        launchFragmentInContainer<HomeFragment>().onFragment { homeFragment ->
            homeFragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                CourseListUiModel(
                    uiModels = listOf(
                        BaseCourseUiModel.FacultyUiModel(
                            id = "professorId",
                            firstName = "firstName",
                            lastName = "lastName",
                            password = "password"
                        ),
                        BaseCourseUiModel.SemesterUiModel(
                            id = "semesterId",
                            startDate = date,
                            endDate = date,
                            name = "Semester"
                        ),
                        BaseCourseUiModel.CourseUiModel(
                            id = "courseId",
                            instructor = "instructor",
                            enrolledStudents = setOf(),
                            assignments = setOf(),
                            semester = "semester",
                            isPublished = false,
                            name = "CourseName",
                            description = "Description"
                        )
                    )
                )
            )
            composeTestRule.onNodeWithText(
                text = "firstName lastName"
            ).assertIsDisplayed()
            composeTestRule.onNodeWithText(
                text = "Semester"
            ).assertIsDisplayed()
            composeTestRule.onNodeWithText(
                text = "CourseName"
            ).assertIsDisplayed()
            composeTestRule.onNodeWithText(
                text = "Description"
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `test fetch courses error`() {
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