package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.ui.model.BaseUiState
import kotlinx.coroutines.flow.flowOf

fun <UseCaseModel : BaseUseCaseModel<UiModel>, Error, UiModel : BaseUiModel> UseCaseResult<UseCaseModel, Error, UiModel>.failure(): BaseUiState<UiModel, Error> {
    val error = error ?: throw IllegalArgumentException("Error is not provided in error event")
    return BaseUiState.Error(error)
}

fun <UseCaseModel : BaseUseCaseModel<UiModel>, Error, UiModel : BaseUiModel> UseCaseResult<UseCaseModel, Error, UiModel>.success(): BaseUiState<UiModel, Error> {
    val data = data ?: throw IllegalArgumentException("Data is not provided in success event")
    return BaseUiState.Success(data.toUiModel())
}

fun <UseCaseModel : BaseUseCaseModel<UIModel>, Error, UIModel : BaseUiModel> failureFlow(error: Error) = flowOf(UseCaseResult.Failure<UseCaseModel, Error, UIModel>(error))
