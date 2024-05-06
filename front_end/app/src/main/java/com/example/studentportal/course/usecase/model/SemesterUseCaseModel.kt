package com.example.studentportal.course.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.SemesterUiModel
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class SemesterUseCaseModel(
    val id: String,
    val name: String,
    val startDate: Date,
    val endDate: Date
) : BaseUseCaseModel<SemesterUiModel> {
    override fun toUiModel(): SemesterUiModel {
        return SemesterUiModel(
            id,
            name,
            startDate,
            endDate
        )
    }
}
