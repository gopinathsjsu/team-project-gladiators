package com.example.studentportal.common.ui.model

fun BaseUiResult<*, *>.isLoading(): Boolean {
    return this is BaseUiResult.Loading
}

fun BaseUiResult<*, *>.isSuccess(): Boolean {
    return this is BaseUiResult.Success
}

fun BaseUiResult<*, *>.isError(): Boolean {
    return this is BaseUiResult.Error
}

fun <UiModel : BaseUiModel, Error> BaseUiResult<UiModel, Error>.data(): UiModel {
    return if (this is BaseUiResult.Success) {
        data
    } else {
        throw IllegalArgumentException("Data is not provided")
    }
}

fun <UiModel : BaseUiModel, Error> BaseUiResult<UiModel, Error>.error(): Error {
    return if (this is BaseUiResult.Error) {
        error
    } else {
        throw IllegalArgumentException("Error Data is not provided")
    }
}