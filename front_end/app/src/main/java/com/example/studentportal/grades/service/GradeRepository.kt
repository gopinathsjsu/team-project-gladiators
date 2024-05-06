package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
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

    suspend fun updateGrade(grade: GradeUseCaseModel): Response<GradeUseCaseModel> {
        val response = provider.service().updateGrade(grade).execute()
        val responseBody = response.body()
        return if (response.isSuccessful && responseBody != null) {
            Response.success(
                GradeUseCaseModel(
                    id = responseBody.id,
                    score = responseBody.score,
                    studentFirstName = "",
                    studentLastName = "",
                    studentId = responseBody.studentId,
                    submissionLink = responseBody.submissionLink
                ))
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
