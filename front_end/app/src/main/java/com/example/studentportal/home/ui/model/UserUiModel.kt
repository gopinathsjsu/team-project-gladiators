package com.example.studentportal.home.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel

data class UserUiModel(
    val id: String,
    val name: String,
    val email: String,
    val type: UserType
) : BaseUiModel
