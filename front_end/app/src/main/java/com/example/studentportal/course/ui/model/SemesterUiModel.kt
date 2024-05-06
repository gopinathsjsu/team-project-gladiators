package com.example.studentportal.course.ui.model

import com.example.studentportal.common.ui.layout.ExpandableItem
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.course.usecase.model.SemesterUseCaseModel
import java.util.Date

data class SemesterUiModel(
    override val id: String,
    val name: String,
    val startDate: Date,
    val endDate: Date
) : BaseUiModel, ExpandableItem {

    override val text: String
        get() = this.name

    fun toUseCaseModel(): SemesterUseCaseModel {
        return SemesterUseCaseModel(
            id,
            name,
            startDate,
            endDate
        )
    }
}
