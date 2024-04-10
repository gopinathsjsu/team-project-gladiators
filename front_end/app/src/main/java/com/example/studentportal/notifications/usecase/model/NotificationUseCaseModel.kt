package com.example.studentportal.notifications.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.notifications.ui.model.NotificationType
import com.example.studentportal.notifications.ui.model.NotificationUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  NotificationUseCaseModel(
    val id: String,
    val courseName: String,
    val eventTitle: String,
    val eventId: String,
    val type: String, // !!! NotificationType
) : BaseUseCaseModel<NotificationUiModel> {
    override fun toUiModel(): NotificationUiModel {
        return NotificationUiModel(
            id = id,
            courseName = courseName,
            eventTitle = eventTitle,
            eventId = eventId,
            type = when (type) {
                "ASSIGNMENT_CREATED" -> NotificationType.ASSIGNMENT_CREATED
                "ASSIGNMENT_GRADED" -> NotificationType.ASSIGNMENT_GRADED
                else -> NotificationType.ANNOUNCEMENT_CREATED
            }
        )
    }
}