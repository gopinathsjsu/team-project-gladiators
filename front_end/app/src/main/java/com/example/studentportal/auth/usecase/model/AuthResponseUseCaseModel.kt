package com.example.studentportal.auth.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.ui.model.UserUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseUseCaseModel(
    val jwtToken: String,
    val user: AuthenticatedUser
) : BaseUseCaseModel<UserUiModel> {
    override fun toUiModel(): UserUiModel {
        return UserUiModel(
            id = user.id,
            password = user.password,
            biography = user.biography,
            email = user.email,
            phone = user.phone,
            firstName = user.firstName,
            lastName = user.lastName,
            type = UserType.valueOf(this.user.type)
        )
    }
}

@JsonClass(generateAdapter = true)
data class AuthenticatedUser(
    val id: String,
    val password: String,
    val type: String,
    val firstName: String,
    val lastName: String,
    val biography: String,
    val email: String,
    val phone: String,
    val enabled: Boolean,
    val credentialsNonExpired: Boolean,
    val username: String,
    val authorities: Array<String>,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean
)
