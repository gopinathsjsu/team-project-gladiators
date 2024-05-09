package com.example.studentportal.grades.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.grades.ui.model.GradeListUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GradeListUseCaseModel(
    val grades: List<GradeUseCaseModel>
) : BaseUseCaseModel<GradeListUiModel> {
    override fun toUiModel(): GradeListUiModel {
        return GradeListUiModel(
            grades = grades.map { it.toUiModel() }
        )
    }
}
