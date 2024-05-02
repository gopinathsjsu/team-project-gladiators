package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class GradeRepository(
    val provider: ServiceProvider<GradeService>
) {

    suspend fun fetchGradesByAssignment(assignmentId: String, userId: String): Response<GradeListUseCaseModel> {
        val response = provider.service().fetchGradesByAssignment(assignmentId, userId).execute()
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

    suspend fun updateGrade(grade: GradeUiModel): Response<Unit> {
        return try {
            val response = provider.service().updateGrade(grade).execute()
            if (response.isSuccessful) {
                Response.success(Unit) // Indicate success
            } else {
                // Handle the failure case by returning or throwing an appropriate exception
                Response.error(
                    response.code(),
                    response.errorBody() ?: throw IllegalAccessException("Response does not contain an error body")
                )
            }
        } catch (e: Exception) {
            // Handle exceptions possibly thrown by network issues, serialization/deserialization issues, etc.
            throw RuntimeException("Network call failed", e)
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
