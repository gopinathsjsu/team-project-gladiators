package com.example.studentportal.syllabus.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel

data class SyllabusUiModel(
    val id: String = "",
    val instructor: String? = null,
    val assignments: List<String> = emptyList(),
    val semester: String = "",
    val name: String = "",
    val description: String = ""
) : BaseUiModel {
    companion object {
        fun empty() = SyllabusUiModel()
    }
}
