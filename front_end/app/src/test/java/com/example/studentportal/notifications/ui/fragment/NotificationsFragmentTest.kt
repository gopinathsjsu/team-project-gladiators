package com.example.studentportal.notifications.ui.fragment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.notifications.ui.model.NotificationListUiModel
import com.example.studentportal.notifications.ui.model.NotificationType
import com.example.studentportal.notifications.ui.model.NotificationUiModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class NotificationsFragmentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test initial setup`() {
        launchFragmentInContainer<NotificationsFragment>().onFragment { fragment ->
            // Test Loading State
            fragment.viewModel._uiResultLiveData.postValue(BaseUiState.Loading())
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()

            // Test Success State - Empty State
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    NotificationListUiModel(
                        listOf()
                    )
                )
            )
            composeTestRule.onNodeWithText("You do not have notifications").assertIsDisplayed()

            // Test Success State - Multiple items
            fragment.viewModel._uiResultLiveData.postValue(
                BaseUiState.Success(
                    NotificationListUiModel(
                        listOf(
                            NotificationUiModel(
                                id = "Notification1",
                                courseName = "Course",
                                eventTitle = "Event Title",
                                eventId = "Event ID",
                                type = NotificationType.ANNOUNCEMENT_CREATED
                            ),
                            NotificationUiModel(
                                id = "Notification1",
                                courseName = "Course",
                                eventTitle = "Event Title",
                                eventId = "Event ID",
                                type = NotificationType.ASSIGNMENT_CREATED
                            ),
                            NotificationUiModel(
                                id = "Notification1",
                                courseName = "Course",
                                eventTitle = "Event",
                                eventId = "Event ID",
                                type = NotificationType.ASSIGNMENT_GRADED
                            )
                        )
                    )
                )
            )
            composeTestRule.apply {
                onNodeWithText("New announcement").assertIsDisplayed()
                onNodeWithText("New assignment").assertIsDisplayed()
                onNodeWithText("An assignment was graded").assertIsDisplayed()
                onNodeWithText("Course: Event").assertIsDisplayed()
            }
        }
    }

    @Test(expected = IllegalAccessException::class)
    fun `expect exception when binding is accessed after UI is destroyed`() {
        var fragment: NotificationsFragment? = null
        launchFragmentInContainer<NotificationsFragment>().onFragment {
            fragment = it
        }.moveToState(Lifecycle.State.DESTROYED)
        fragment?.binding // Force Crash
    }
}
