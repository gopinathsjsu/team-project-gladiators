package com.example.studentportal.assignment.usecase.models

import com.example.studentportal.assignment.ui.model.AssignmentListUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AssignmentListUseCaseModel(
    private val useCaseModels: List<AssignmentUseCaseModel>
) : BaseUseCaseModel<AssignmentListUiModel> {
    override fun toUiModel(): AssignmentListUiModel {
        return AssignmentListUiModel(
            uiModels = useCaseModels.map {
                it.toUiModel()
            }
        )
    }
}
