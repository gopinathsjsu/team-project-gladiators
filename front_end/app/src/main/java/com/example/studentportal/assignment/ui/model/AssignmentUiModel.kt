package com.example.studentportal.assignment.ui.model

import android.os.Parcelable
import com.example.studentportal.assignment.usecase.models.AssignmentUseCaseModel
import com.example.studentportal.common.ui.model.BaseUiModel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class AssignmentUiModel(
    val id: String,
    val dueDate: Date,
    val name: String,
    val submissions: Set<String>,
    val course: String,
    val link: String
) : BaseUiModel, Parcelable {
    fun toUseCaseModel(): AssignmentUseCaseModel {
        return AssignmentUseCaseModel(
            id = id,
            dueDate = dueDate,
            name = name,
            submissions = submissions,
            link = link,
            course = course
        )
    }
}
