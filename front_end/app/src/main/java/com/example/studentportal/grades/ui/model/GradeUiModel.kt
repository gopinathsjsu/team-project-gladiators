package com.example.studentportal.grades.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel

data class GradeUiModel(
    val gradeId: String,
    val score: Int,
    val studentFirstName: String,
    val studentLastName: String,
    val studentId: String
) : BaseUiModel
