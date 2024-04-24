package com.example.studentportal.notifications.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.notifications.service.repository.NotificationRepository
import com.example.studentportal.notifications.ui.model.NotificationListUiModel
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import kotlinx.coroutines.flow.Flow

class NotificationListUseCase(
    override val repository: NotificationRepository
) : BaseUseCase<NotificationListUseCaseModel, DefaultError, NotificationRepository, NotificationListUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<NotificationListUseCaseModel, DefaultError, NotificationListUiModel>> {
        return try {
            // CHANGE MOCK SOURCE
            val response = repository.fetchNotifications()
            val notifications = response.body()
            val errorResponse = response.errorBody()
            when {
                notifications != null -> successFlow(notifications)
                errorResponse != null -> defaultFailureFlow(errorResponse)
                else -> defaultFailureFlow()
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
