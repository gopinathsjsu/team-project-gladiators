package com.example.studentportal.students.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.students.ui.model.StudentType
import com.example.studentportal.students.ui.model.StudentUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentUseCaseModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val type: String, // Assume types are "UNDERGRADUATE", "GRADUATE", "PHD"
    val biography: String? // Adding biography to the use case model
) : BaseUseCaseModel<StudentUiModel> {
    override fun toUiModel(): StudentUiModel {
        return StudentUiModel(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            type = when (type) {
                "UNDERGRADUATE" -> StudentType.UNDERGRADUATE
                "GRADUATE" -> StudentType.GRADUATE
                else -> StudentType.PHD
            },
            biography = biography // Passing the biography as it is from the use case model
        )
    }
}
