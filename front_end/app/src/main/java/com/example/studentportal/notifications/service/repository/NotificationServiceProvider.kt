package com.example.studentportal.notifications.service.repository

import com.example.studentportal.notifications.service.NotificationService
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import retrofit2.Retrofit

class NotificationServiceProvider : ServiceProvider<NotificationService> {
    override val retrofit: Retrofit get() = koin.get()

    override fun service(): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }
}