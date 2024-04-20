package com.example.studentportal.grades.service

import GradeService
import android.util.Log
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class GradeRepository(
    val provider: ServiceProvider<GradeService>
) {

    suspend fun fetchGradesByAssignment(assignmentId: String): Response<GradeListUseCaseModel> {
        Log.d("Grades", "provided assignmentId: $assignmentId")

        val response = provider.service().fetchGradesByAssignment(assignmentId).execute()
        Log.d("Grades", "response is successful: ${response.isSuccessful}")
        Log.d("Grades", "response: \n${response.body().orEmpty()}")
        return if (response.isSuccessful) {
            Response.success(
                GradeListUseCaseModel(
                    grades = response.body().orEmpty()
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
                    GradeRepository(
                        provider = GradeServiceProvider()
                    )
                }
            }
        }
    }
}
