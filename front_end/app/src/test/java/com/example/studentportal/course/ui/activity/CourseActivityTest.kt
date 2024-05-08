package com.example.studentportal.course.ui.activity

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.course.ui.activtiy.CourseActivity
import com.example.studentportal.course.ui.activtiy.CourseActivity.Companion.KEY_COURSE
import com.example.studentportal.course.ui.fragment.CourseFragment
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.home.ui.layout.KEY_USER_ID
import com.example.studentportal.home.ui.layout.KEY_USER_TYPE
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class CourseActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test initial setup`() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), CourseActivity::class.java)
        intent.putExtra(KEY_USER_TYPE, UserType.FACULTY.name)
        intent.putExtra(KEY_USER_ID, "userId")
        intent.putExtra(
            KEY_COURSE,
            BaseCourseUiModel.CourseUiModel(
                id = "courseId",
                instructor = "",
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = "Semester",
                isPublished = false,
                name = "Name",
                description = "Description"
            )
        )
        ActivityScenario.launch<CourseActivity>(intent)
            .use { scenario ->
                scenario.onActivity { activity ->
                    val fragmentManager = activity.supportFragmentManager

                    // Verify homeFragment is displayed
                    val fragment = fragmentManager.findFragmentByTag(CourseFragment.TAG) as CourseFragment
                    assertThat(fragment).isNotNull()

                    fragment.viewModel._uiResultLiveData.value = BaseUiState.Success(
                        CourseDetailsUiModel(
                            courseUiModel = BaseCourseUiModel.CourseUiModel(
                                id = "courseId",
                                instructor = "",
                                enrolledStudents = setOf(),
                                assignments = setOf(),
                                semester = "Semester",
                                isPublished = false,
                                name = "Name",
                                description = "Description"
                            ),
                            instructorUiModel = BaseCourseUiModel.FacultyUiModel(
                                id = "ID",
                                password = "Password",
                                firstName = "FirstName",
                                lastName = "LastName"
                            ),
                            announcements = listOf()
                        )
                    )

                    // Verify options
                    composeTestRule.onNodeWithText("Assignments").assertIsDisplayed()
                    composeTestRule.onNodeWithText("Assignments").performClick() // Consume click

                    // Verify backstack is empty
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(0)
                }
            }
    }
}
