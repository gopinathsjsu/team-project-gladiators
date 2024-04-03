package com.example.studentportal.common.ui.model

fun BaseUiState<*, *>.isLoading(): Boolean {
    return this is BaseUiState.Loading
}

fun BaseUiState<*, *>.isSuccess(): Boolean {
    return this is BaseUiState.Success
}

fun BaseUiState<*, *>.isError(): Boolean {
    return this is BaseUiState.Error
}

fun <UiModel : BaseUiModel, Error> BaseUiState<UiModel, Error>?.data(): UiModel? {
    return if (this is BaseUiState.Success) {
        data
    } else {
        throw IllegalArgumentException("Data is not provided")
    }
}

fun <UiModel : BaseUiModel, Error> BaseUiState<UiModel, Error>?.error(): Error? {
    return if (this is BaseUiState.Error) {
        error
    } else {
        throw IllegalArgumentException("Error Data is not provided")
    }
}
