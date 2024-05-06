package com.example.studentportal.home.service

import com.example.studentportal.course.usecase.model.CourseDetailsUseCaseModel
import com.example.studentportal.course.usecase.model.CourseInputUseCaseModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseService {
    @GET("/courses")
    fun fetchCourses(@Query("userId") userId: String): Call<List<BaseCourseUseCaseModel>>

    @GET("/courses/input")
    fun fetchCourseInputData(): Call<CourseInputUseCaseModel>

    @GET("/courses/{courseId}/details")
    fun fetchCourseDetails(@Path("courseId") userId: String): Call<CourseDetailsUseCaseModel>

    @POST("/courses")
    fun createCourse(@Body courseUseCaseModel: BaseCourseUseCaseModel.CourseUseCaseModel): Call<List<BaseCourseUseCaseModel>>

    @POST("courses/update")
    fun updateCourse(@Body courseUseCaseModel: BaseCourseUseCaseModel.CourseUseCaseModel): Call<CourseDetailsUseCaseModel>
}
