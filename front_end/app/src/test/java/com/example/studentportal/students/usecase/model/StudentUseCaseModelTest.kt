package com.example.studentportal.students.usecase.model

import com.example.studentportal.students.ui.model.StudentType
import com.example.studentportal.students.ui.model.StudentUiModel
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class StudentUseCaseModelTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test StudentUseCaseModel to StudentUiModel transformation`() {
        // Arrange
        val studentUseCaseModel = StudentUseCaseModel(
            id = "123",
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = "555-1234",
            type = "GRADUATE",
            biography = "A biography about John Doe."
        )

        // Act
        val result = studentUseCaseModel.toUiModel()

        // Assert
        assertThat(result).isEqualTo(
            StudentUiModel(
                id = "123",
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                phone = "555-1234",
                type = StudentType.GRADUATE,
                biography = "A biography about John Doe."
            )
        )
    }

    @Test
    fun `test StudentUseCaseModel with missing optional fields`() {
        // Arrange
        val studentUseCaseModel = StudentUseCaseModel(
            id = "124",
            firstName = "Jane",
            lastName = "Smith",
            email = "jane.smith@example.com",
            phone = null, // Phone is null
            type = "PHD",
            biography = null // Biography is null
        )

        // Act
        val result = studentUseCaseModel.toUiModel()

        // Assert
        val expected = StudentUiModel(
            id = "124",
            firstName = "Jane",
            lastName = "Smith",
            email = "jane.smith@example.com",
            phone = null,
            type = StudentType.PHD,
            biography = null
        )

        assertThat(result).isEqualTo(expected)
    }
}
