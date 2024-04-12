package com.example.studentportal.notifications.usecase.model

import com.example.studentportal.notifications.ui.model.NotificationListUiModel
import com.example.studentportal.notifications.ui.model.NotificationType
import com.example.studentportal.notifications.ui.model.NotificationUiModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NotificationUseCaseModelTest {

    @Test
    fun `test NotificiationList model`() {
        // Arrange
        val notificationListUseCaseModel = NotificationListUseCaseModel(
            listOf(
                NotificationUseCaseModel(
                    id = "ID",
                    courseName = "COURSE_NAME",
                    eventTitle = "EVENT_TITLE",
                    eventId = "EVENT_ID",
                    type = "ASSIGNMENT_GRADED"
                )
            )
        )

        // Act
        val result = notificationListUseCaseModel.toUiModel()

        // Assert
        assertThat(result).isEqualTo(
            NotificationListUiModel(
                listOf(
                    NotificationUiModel(
                        id = "ID",
                        courseName = "COURSE_NAME",
                        eventTitle = "EVENT_TITLE",
                        eventId = "EVENT_ID",
                        type = NotificationType.ASSIGNMENT_GRADED
                    )
                )
            )
        )
    }
}
