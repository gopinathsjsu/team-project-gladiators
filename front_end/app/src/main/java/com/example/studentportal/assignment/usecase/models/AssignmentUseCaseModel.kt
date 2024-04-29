package com.example.studentportal.assignment.usecase.models

import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
class AssignmentUseCaseModel(
    val id: String,
    val dueDate: Date,
    val name: String,
    val submissions: Set<String>,
    val course: String,
    val link: String
) : BaseUseCaseModel<AssignmentUiModel> {
    override fun toUiModel(): AssignmentUiModel {
        return AssignmentUiModel(
            id = id,
            dueDate = dueDate,
            name = name,
            submissions = submissions,
            link = link,
            course = course
        )
    }
}
