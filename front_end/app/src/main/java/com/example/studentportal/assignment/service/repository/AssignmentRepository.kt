package com.example.studentportal.assignment.service.repository

import com.example.studentportal.assignment.service.AssignmentService
import com.example.studentportal.assignment.usecase.models.AssignmentListUseCaseModel
import com.example.studentportal.assignment.usecase.models.AssignmentUseCaseModel
import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.serviceModule
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class AssignmentRepository(
    override val provider: AssignmentServiceProvider
) : Repository<AssignmentService> {

    suspend fun fetchAssignments(courseId: String): Response<AssignmentListUseCaseModel> {
        val response = provider.service().fetchAssignments(courseId).execute()
        return if (response.isSuccessful) {
            Response.success(
                AssignmentListUseCaseModel(
                    useCaseModels = response.body().orEmpty()
                )
            )
        } else {
            val error = response.errorBody()
                ?: throw IllegalAccessException("Response does not return error")
            Response.error(response.code(), error)
        }
    }

    suspend fun createNewAssignment(assignment: AssignmentUseCaseModel): Response<AssignmentListUseCaseModel> {
        val response = provider.service().postAssignment(assignment).execute()
        return if (response.isSuccessful) {
            Response.success(
                AssignmentListUseCaseModel(
                    useCaseModels = response.body().orEmpty()
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
                    AssignmentRepository(
                        provider = AssignmentServiceProvider()
                    )
                }
            }
        }
    }
}
