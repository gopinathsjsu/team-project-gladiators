package com.example.studentportal.profile.service

import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): Call<UserUseCaseModel>
}
