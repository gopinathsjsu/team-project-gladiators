package com.example.studentportal.home.usecase.models


import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel

class StudentUseCaseModel(
    val id: String,
    val name: String,
    val email: String
): BaseUseCaseModel<UserUiModel>{
    override fun toUiModel(): UserUiModel {
        return UserUiModel(
            id = id,
            name = name,
            email = email,
            type = UserType.STUDENT
        )
    }
}