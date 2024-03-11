package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel

sealed class UseCaseResult<UseCaseModel : BaseUseCaseModel<UiModel>, Error, UiModel : BaseUiModel>(val data: UseCaseModel?, val error: Error?) {
    class Success<UseCaseModel : BaseUseCaseModel<UiModel>, Error, UiModel : BaseUiModel>(data: UseCaseModel) : UseCaseResult<UseCaseModel, Error, UiModel>(data, null)
    class Failure<UseCaseModel : BaseUseCaseModel<UiModel>, Error, UiModel : BaseUiModel>(error: Error) : UseCaseResult<UseCaseModel, Error, UiModel>(null, error)
}