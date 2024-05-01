package com.example.studentportal.profile.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.course.ui.model.UserType

data class UserUiModel(
    val id: String = "",
    val password: String = "",
    val biography: String = "",
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val type: UserType = UserType.UNKNOWN
) : BaseUiModel {
    companion object {
        fun empty() = UserUiModel()
    }
}
