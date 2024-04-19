package com.example.studentportal.notifications.service.repository

import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.notifications.mock.MockNotificationListDataSource
import com.example.studentportal.notifications.service.NotificationService
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class NotificationRepository(
    override val provider: ServiceProvider<NotificationService>
) : Repository<NotificationService> {

    suspend fun fetchNotifications(): Response<NotificationListUseCaseModel> {
        return provider.service().fetchNotifications().execute()
    }

    // Fetching mock list of notifications
    fun fetchMockNotifications(): Response<NotificationListUseCaseModel> {
        return Response.success(MockNotificationListDataSource.getMockNotificationList())
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    NotificationRepository(
                        provider = NotificationServiceProvider()
                    )
                }
            }
        }
    }
}