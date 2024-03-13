package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel

interface BaseUseCaseProvider<
    UseCase : BaseUseCase<UseCaseModel, Error, Repository, UiModel>,
    UseCaseModel : BaseUseCaseModel<UiModel>,
    UiModel : BaseUiModel,
    Error,
    Repository> {
    fun provideUseCase(): UseCase
}
