package com.example.studentportal.profile.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.ui.model.UserUiModel

data class UserUseCaseModel(
    val id: String,
    val password: String,
    val biography: String,
    val email: String,
    val phone: String,
    val firstName: String,
    val lastName: String,
    val type: String
) : BaseUseCaseModel<UserUiModel> {
    override fun toUiModel(): UserUiModel {
        return UserUiModel(
            id = id,
            password = password,
            biography = biography,
            email = email,
            phone = phone,
            firstName = firstName,
            lastName = lastName,
            type = UserType.valueOf(this.type)
        )
    }
}
