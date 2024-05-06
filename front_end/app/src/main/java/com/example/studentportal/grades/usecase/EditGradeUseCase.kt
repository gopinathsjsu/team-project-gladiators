package com.example.studentportal.grades.usecase

import android.util.Log
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.grades.service.GradeRepository
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import kotlinx.coroutines.flow.Flow

class EditGradeUseCase(
    private val grade: GradeUseCaseModel,
    override val repository: GradeRepository
) : BaseUseCase<GradeUseCaseModel, DefaultError, GradeRepository, GradeUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<GradeUseCaseModel, DefaultError, GradeUiModel>> {
        return try {
            val response = repository.updateGrade(grade)
            val grades = response.body()
            Log.d("USECASE", "response: $grades")
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
