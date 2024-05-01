package com.example.studentportal.profile.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import kotlinx.coroutines.flow.Flow

class UserProfileUseCase(
    private val userId: String,
    override val repository: UserRepository
) : BaseUseCase<UserUseCaseModel, DefaultError, UserRepository, UserUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<UserUseCaseModel, DefaultError, UserUiModel>> {
        return try {
            val response = repository.fetchUser(userId)
            val user = response.body()
            val error = response.errorBody()
            when {
                user != null -> successFlow(user)
                error != null -> defaultFailureFlow(code = response.code(), error)
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
