package com.example.studentportal.common.service.models

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel

interface BaseServiceModel<UseCaseModel : BaseUseCaseModel<UiModel>, UiModel : BaseUiModel> {
    fun toUseCaseModel(): UseCaseModel
}
