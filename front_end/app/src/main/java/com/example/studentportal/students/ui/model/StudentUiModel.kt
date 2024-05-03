package com.example.studentportal.students.ui.model

import android.os.Parcelable
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.students.usecase.model.StudentUseCaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentUiModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val type: StudentType,
    val biography: String?
) : BaseUiModel, Parcelable {

    fun toUseCaseModel(): StudentUseCaseModel {
        return StudentUseCaseModel(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            type = type.toString(),
            biography = biography
        )
    }
}
