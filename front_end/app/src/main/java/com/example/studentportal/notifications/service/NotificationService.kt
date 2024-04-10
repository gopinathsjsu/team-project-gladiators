package com.example.studentportal.notifications.service
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import com.example.studentportal.notifications.usecase.model.NotificationUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {
    @GET("/notifications/{Id}")
    fun fetchNotification(@Path("Id") id: String): Call<NotificationUseCaseModel>

    @GET("/notifications")
    fun fetchNotifications(): Call<NotificationListUseCaseModel>
}