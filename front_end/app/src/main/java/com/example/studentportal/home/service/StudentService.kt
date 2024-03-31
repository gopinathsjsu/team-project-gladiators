package com.example.studentportal.home.service

import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {
    @GET("/students/{Id}")
    fun fetchStudent(@Path("Id") id: String): Call<StudentUseCaseModel>
}
