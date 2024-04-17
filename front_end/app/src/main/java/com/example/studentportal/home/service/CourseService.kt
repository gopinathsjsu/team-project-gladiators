package com.example.studentportal.home.service

import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseService {
    @GET("/courses")
    fun fetchCourses(@Query("userId") userId: String): Call<List<BaseCourseUseCaseModel>>
}
