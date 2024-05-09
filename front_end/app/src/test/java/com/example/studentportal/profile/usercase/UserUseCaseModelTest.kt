package com.example.studentportal.profile.usercase

import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class UserUseCaseModelTest {

    @Test
    fun `test UserUseCaseModel`() {
        val user = UserUseCaseModel(
            id = "id",
            password = "password",
            biography = "biography",
            email = "email",
            phone = "phone",
            firstName = "firstName",
            lastName = "lastName",
            type = UserType.FACULTY.name
        )
        assertThat(user.toUiModel()).isEqualTo(
            UserUiModel(
                id = "id",
                password = "password",
                biography = "biography",
                email = "email",
                phone = "phone",
                firstName = "firstName",
                lastName = "lastName",
                type = UserType.FACULTY
            )
        )
    }
}
