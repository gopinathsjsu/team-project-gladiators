package com.example.studentportal.notifications.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel

data class NotificationUiModel(
    val id: String,
    val courseName: String,
    val eventTitle: String,
    val eventId: String,
    val type: NotificationType
) : BaseUiModel
