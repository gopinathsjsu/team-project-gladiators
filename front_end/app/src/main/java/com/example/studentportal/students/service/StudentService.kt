package com.example.studentportal.students.service
import com.example.studentportal.students.usecase.model.StudentUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {

    @GET("/students/{courseId}")
    fun fetchStudents(@Path("courseId") userId: String): Call<List<StudentUseCaseModel>>
}
