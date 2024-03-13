package com.example.studentportal.common.ui.model

sealed class BaseUiResult<UiModel : BaseUiModel, Error> {
    class Loading<UiModel : BaseUiModel, Error> : BaseUiResult<UiModel, Error>()
    class Success<UiModel : BaseUiModel, Error>(val data: UiModel) : BaseUiResult<UiModel, Error>()
    class Error<UiModel : BaseUiModel, Error>(val error: Error) : BaseUiResult<UiModel, Error>()
}
