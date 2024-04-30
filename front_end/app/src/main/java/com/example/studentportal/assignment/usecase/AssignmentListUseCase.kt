package com.example.studentportal.assignment.usecase

import com.example.studentportal.assignment.service.repository.AssignmentRepository
import com.example.studentportal.assignment.ui.model.AssignmentListUiModel
import com.example.studentportal.assignment.usecase.models.AssignmentListUseCaseModel
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow

class AssignmentListUseCase(
    private val courseId: String,
    override val repository: AssignmentRepository
) : BaseUseCase<AssignmentListUseCaseModel, DefaultError, AssignmentRepository, AssignmentListUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<AssignmentListUseCaseModel, DefaultError, AssignmentListUiModel>> {
        return try {
            val response = repository.fetchAssignments(courseId)
            val assignments = response.body()
            val errorResponse = response.errorBody()
            return when {
                assignments != null -> successFlow(assignments)
                errorResponse != null -> defaultFailureFlow(errorResponse)
                else -> defaultFailureFlow()
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
