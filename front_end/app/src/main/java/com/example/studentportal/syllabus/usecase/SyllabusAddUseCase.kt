package com.example.studentportal.syllabus.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.syllabus.service.repository.SyllabusRepository
import com.example.studentportal.syllabus.ui.model.SyllabusUiModel
import com.example.studentportal.syllabus.usecase.model.SyllabusUseCaseModel
import kotlinx.coroutines.flow.Flow

class SyllabusAddUseCase(
    val courseId: String,
    val description: String,
    override val repository: SyllabusRepository
) : BaseUseCase<SyllabusUseCaseModel, DefaultError, SyllabusRepository, SyllabusUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<SyllabusUseCaseModel, DefaultError, SyllabusUiModel>> {
        return try {
            val response = repository.updateDescription(courseId, description)
            val syllabus = response.body()
            val error = response.errorBody()
            when {
                syllabus != null -> successFlow(syllabus)
                error != null -> defaultFailureFlow(code = response.code(), error)
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
