package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<UseCaseModel : BaseUseCaseModel<UiModel>, Error, Repository, UiModel : BaseUiModel> {

    val repository: Repository
    suspend fun launch(): Flow<UseCaseResult<UseCaseModel, Error, UiModel>>
}