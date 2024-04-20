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
    override val repository: GradeRepository
) : BaseUseCase<GradeListUseCaseModel, DefaultError, GradeRepository, GradeListUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<GradeListUseCaseModel, DefaultError, GradeListUiModel>> {
        return try {
            // CHANGE MOCK SOURCE
            val response = repository.fetchGradesByAssignment(assignmentId)
            val notifications = response.body()
            val errorResponse = response.errorBody()
            when {
                notifications != null -> successFlow(notifications)
                errorResponse != null -> defaultFailureFlow(errorResponse)
                else -> defaultFailureFlow()
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}