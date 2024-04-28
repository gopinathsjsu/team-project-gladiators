package com.example.studentportal.assignment.service

import com.example.studentportal.assignment.usecase.models.AssignmentUseCaseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AssignmentService {
    @GET("/assignments/{courseId}")
    fun fetchAssignments(@Path("courseId") userId: String): Call<List<AssignmentUseCaseModel>>

    @POST("/assignments")
    fun postAssignment(@Body assignment: AssignmentUseCaseModel): Call<List<AssignmentUseCaseModel>>
}
