package com.example.studentportal.home.usecase.model

import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserUseCaseModelTest {

    @Test
    fun `test UseCaseModel`() {
        // Arrange
        val useCaseModel = StudentUseCaseModel(
            id = "ID",
            name = "NAME",
            email = "EMAIL"
        )

        // Act
        val result = useCaseModel.toUiModel()

        // Assert
        assertThat(result).isEqualTo(
            UserUiModel(
                id = "ID",
                name = "NAME",
                email = "EMAIL",
                type = UserType.STUDENT
            )
        )
    }
}
