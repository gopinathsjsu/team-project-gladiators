package com.example.studentportal.syllabus.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.syllabus.ui.model.SyllabusUiModel

data class SyllabusUseCaseModel(
    val id: String,
    val instructor: String?,
    val assignments: List<String>,
    val semester: String,
    val name: String,
    val description: String
) : BaseUseCaseModel<SyllabusUiModel> {
    override fun toUiModel(): SyllabusUiModel {
        return SyllabusUiModel(
            id = id,
            instructor = instructor,
            assignments = assignments,
            semester = semester,
            name = name,
            description = description
        )
    }
}
