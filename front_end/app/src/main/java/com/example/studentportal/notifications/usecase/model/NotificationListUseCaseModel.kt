package com.example.studentportal.notifications.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.notifications.ui.model.NotificationListUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationListUseCaseModel(
    val notifications: List<NotificationUseCaseModel>
) : BaseUseCaseModel<NotificationListUiModel> {
    override fun toUiModel(): NotificationListUiModel {
        return NotificationListUiModel(
            notifications = notifications.map { it.toUiModel() }
        )
    }
}
