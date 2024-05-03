package com.example.studentportal.students.service.repository

import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.students.service.StudentService
import com.example.studentportal.students.usecase.model.StudentListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class StudentRepository(
    override val provider: ServiceProvider<StudentService>
) : Repository<StudentService> {

    suspend fun fetchStudents(courseId: String): Response<StudentListUseCaseModel> {
        val response = provider.service().fetchStudents(courseId).execute()
        return if (response.isSuccessful) {
            Response.success(
                StudentListUseCaseModel(
                    students = response.body().orEmpty()
                )
            )
        } else {
            val error = response.errorBody()
                ?: throw IllegalAccessException("Response does not return error")
            Response.error(response.code(), error)
        }
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    StudentRepository(
                        provider = StudentServiceProvider()
                    )
                }
            }
        }
    }
}
