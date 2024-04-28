package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
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
