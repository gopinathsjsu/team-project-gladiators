package com.example.studentportal.syllabus.service

import com.example.studentportal.syllabus.usecase.model.SyllabusUseCaseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SyllabusService {
    @GET("/courses/{courseId}/content")
    fun getCourse(
        @Path("courseId") courseId: String
    ): Call<SyllabusUseCaseModel>

    @POST("/courses/{courseId}/description")
    fun updateDescription(@Path("courseId") courseId: String, @Body description: String): Call<SyllabusUseCaseModel>

    @PUT("/courses/{courseId}/description")
    fun editDescription(@Path("courseId") courseId: String, @Body description: String): Call<SyllabusUseCaseModel>
}
