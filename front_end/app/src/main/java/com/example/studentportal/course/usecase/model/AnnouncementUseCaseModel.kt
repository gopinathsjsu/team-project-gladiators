package com.example.studentportal.course.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.AnnouncementUiModel

data class AnnouncementUseCaseModel(
    val id: String,
    val courseId: String,
    val title: String,
    val description: String
) : BaseUseCaseModel<AnnouncementUiModel> {
    override fun toUiModel(): AnnouncementUiModel {
        return AnnouncementUiModel(
            id = id,
            courseId = courseId,
            title = title,
            description = description
        )
    }
}
