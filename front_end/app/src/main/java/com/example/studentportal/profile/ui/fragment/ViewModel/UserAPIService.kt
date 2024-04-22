package com.example.studentportal.profile.ui.fragment.ViewModel

import retrofit2.Call
import retrofit2.http.GET

interface UserAPIService {
    @GET("/users")
    fun getUsers(): Call<List<User>>
}
data class User(
    val id: String,
    val password: String,
    val type: String,
    val firstName: String,
    val lastName: String,
    val biography: String,
    val email: String,
    val phone: String
)
