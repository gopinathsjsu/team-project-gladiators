package com.example.studentportal.home.service.models

import com.example.studentportal.common.service.models.BaseServiceModel
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class StudentServiceModel(
    private val id: String,
    private val name: String,
    private val email: String
): BaseServiceModel<StudentUseCaseModel, UserUiModel>{
    override fun toUseCaseModel(): StudentUseCaseModel {
        return StudentUseCaseModel(
            id = id,
            name = name,
            email = email
        )
    }
}