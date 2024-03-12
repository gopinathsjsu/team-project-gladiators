package com.example.studentportal.home.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultUseCaseError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.home.service.repository.StudentRepository
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import kotlinx.coroutines.flow.Flow

class StudentUseCase(
    val userId: String,
    override val repository: StudentRepository
) : BaseUseCase<StudentUseCaseModel, DefaultUseCaseError, StudentRepository, UserUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<StudentUseCaseModel, DefaultUseCaseError, UserUiModel>> {
        val response = repository.fetchStudent(userId)
        val student = response.body()
        val errorResponse = response.errorBody()
        return when {
            student != null -> successFlow(student)
            errorResponse != null -> defaultFailureFlow(errorResponse)
            else -> defaultFailureFlow()
        }
    }
}
