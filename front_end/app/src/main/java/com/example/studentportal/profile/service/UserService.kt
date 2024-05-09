package com.example.studentportal.profile.service

import com.example.studentportal.auth.usecase.model.AuthRequest
import com.example.studentportal.auth.usecase.model.AuthResponseUseCaseModel
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): Call<UserUseCaseModel>

    @POST("/token")
    fun login(
        @Body authRequest: AuthRequest
    ): Call<AuthResponseUseCaseModel>

    @POST("/users")
    fun updateUser(@Body user: UserUseCaseModel): Call<UserUseCaseModel>
}
