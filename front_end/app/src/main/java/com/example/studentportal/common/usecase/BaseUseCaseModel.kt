package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel

interface BaseUseCaseModel<Model : BaseUiModel> {
    fun toUiModel(): Model
}
