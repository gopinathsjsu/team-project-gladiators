package com.example.studentportal.auth.usecase

import com.example.studentportal.auth.usecase.model.AuthRequest
import com.example.studentportal.auth.usecase.model.AuthResponseUseCaseModel
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.profile.ui.model.UserUiModel
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val authRequest: AuthRequest,
    override val repository: UserRepository
) : BaseUseCase<AuthResponseUseCaseModel, DefaultError, UserRepository, UserUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<AuthResponseUseCaseModel, DefaultError, UserUiModel>> {
        return try {
            val response = repository.login(authRequest = authRequest)
            val authResponse = response.body()
            val errorResponse = response.errorBody()
            when {
                authResponse != null -> successFlow(authResponse)
                errorResponse != null -> defaultFailureFlow(
                    response.code(),
                    errorBody = errorResponse
                )
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
