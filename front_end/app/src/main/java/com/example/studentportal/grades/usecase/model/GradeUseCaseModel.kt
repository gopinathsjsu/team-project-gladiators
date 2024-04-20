package com.example.studentportal.grades.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.grades.ui.model.GradeUiModel

data class GradeUseCaseModel (
    val gradeId: String,
    val score: Double,
    val studentFirstName: String,
    val studentLastName: String,
    val studentId: String,
) : BaseUseCaseModel<GradeUiModel> {
    override fun toUiModel(): GradeUiModel {
        return GradeUiModel(
            gradeId = gradeId,
            score = score,
            studentFirstName = studentFirstName,
            studentLastName = studentLastName,
            studentId = studentId,
        )
    }
}