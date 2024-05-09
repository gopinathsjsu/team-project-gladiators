package com.example.studentportal.students.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.students.ui.model.StudentListUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentListUseCaseModel(
    val students: List<StudentUseCaseModel>
) : BaseUseCaseModel<StudentListUiModel> {
    override fun toUiModel(): StudentListUiModel {
        return StudentListUiModel(
            students = students.map { it.toUiModel() }
        )
    }
}
