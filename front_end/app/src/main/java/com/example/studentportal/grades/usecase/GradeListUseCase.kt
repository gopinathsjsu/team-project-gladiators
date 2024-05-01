package com.example.studentportal.grades.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.grades.service.GradeRepository
import com.example.studentportal.grades.ui.model.GradeListUiModel
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import kotlinx.coroutines.flow.Flow

class GradeListUseCase(
    private val assignmentId: String,
    private val userId: String,
    override val repository: GradeRepository
) : BaseUseCase<GradeListUseCaseModel, DefaultError, GradeRepository, GradeListUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<GradeListUseCaseModel, DefaultError, GradeListUiModel>> {
        return try {
            val response = repository.fetchGradesByAssignment(assignmentId, userId)
            val grades = response.body()
            val errorResponse = response.errorBody()
            when {
                grades != null -> successFlow(grades)
                errorResponse != null -> defaultFailureFlow(code = response.code(), errorResponse)
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
