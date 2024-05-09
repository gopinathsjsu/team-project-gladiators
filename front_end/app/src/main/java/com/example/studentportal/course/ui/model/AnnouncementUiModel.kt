package com.example.studentportal.course.ui.model

import android.os.Parcelable
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.course.usecase.model.AnnouncementUseCaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnnouncementUiModel(
    val id: String,
    val courseId: String,
    val title: String,
    val description: String
) : BaseUiModel, Parcelable {

    fun toUseCaseModel(): AnnouncementUseCaseModel {
        return AnnouncementUseCaseModel(
            id = id,
            courseId = courseId,
            title = title,
            description = description
        )
    }
}
