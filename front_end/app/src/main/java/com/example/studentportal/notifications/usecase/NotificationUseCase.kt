package com.example.studentportal.notifications.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.notifications.service.repository.NotificationRepository
import com.example.studentportal.notifications.usecase.model.NotificationUseCaseModel
import com.example.studentportal.notifications.ui.model.NotificationUiModel
import kotlinx.coroutines.flow.Flow

class NotificationUseCase(
    val notificationId: String,
    override val repository: NotificationRepository
) : BaseUseCase<NotificationUseCaseModel, DefaultError, NotificationRepository, NotificationUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<NotificationUseCaseModel, DefaultError, NotificationUiModel>> {
        val response = repository.fetchNotification(notificationId)
        val notification = response.body()
        val errorResponse = response.errorBody()
        return when {
            notification != null -> successFlow(notification)
            errorResponse != null -> defaultFailureFlow(errorResponse)
            else -> defaultFailureFlow()
        }
    }
}