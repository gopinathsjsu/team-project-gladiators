package com.example.studentportal.common.ui.model

sealed class BaseUiState<UiModel : BaseUiModel, Error>(val data: UiModel?, val error: Error?) {
    class Loading<UiModel : BaseUiModel, Error> : BaseUiState<UiModel, Error>(null, null)
    class Success<UiModel : BaseUiModel, Error>(data: UiModel) : BaseUiState<UiModel, Error>(data, null)
    class Error<UiModel : BaseUiModel, Error>(error: Error) : BaseUiState<UiModel, Error>(null, error)
}
