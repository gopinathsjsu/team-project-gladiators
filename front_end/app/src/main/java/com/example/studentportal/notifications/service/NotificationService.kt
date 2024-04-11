package com.example.studentportal.notifications.service
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import retrofit2.Call
import retrofit2.http.GET

interface NotificationService {
    @GET("/notifications")
    fun fetchNotifications(): Call<NotificationListUseCaseModel>
}
