package com.example.studentportal.profile.ui.model

import android.os.Parcelable
import com.example.studentportal.common.ui.layout.ExpandableItem
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUiModel(
    override val id: String = "",
    val password: String = "",
    val biography: String = "",
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val type: UserType = UserType.UNKNOWN
) : BaseUiModel, ExpandableItem, Parcelable {

    override val text: String
        get() = "$firstName $lastName"

    fun toUseCaseModel(): UserUseCaseModel {
        return UserUseCaseModel(
            id = id,
            password = password,
            biography = biography,
            email = email,
            phone = phone,
            firstName = firstName,
            lastName = lastName,
            type = this.type.name
        )
    }

    companion object {
        fun empty() = UserUiModel()
    }
}
