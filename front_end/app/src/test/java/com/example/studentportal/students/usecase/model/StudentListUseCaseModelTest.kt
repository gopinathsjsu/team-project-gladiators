package com.example.studentportal.students.usecase.model

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class StudentListUseCaseModelTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `toUiModel correctly transforms list of StudentUseCaseModel to StudentListUiModel`() {
        // Arrange
        val studentUseCaseModels = listOf(
            StudentUseCaseModel(
                id = "1",
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                phone = "555-1234",
                type = "GRADUATE",
                biography = "Some bio"
            ),
            StudentUseCaseModel(
                id = "2",
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@example.com",
                phone = "555-4321",
                type = "UNDERGRADUATE",
                biography = "Another bio"
            )
        )
        val studentListUseCaseModel = StudentListUseCaseModel(
            students = studentUseCaseModels
        )

        // Act
        val uiModel = studentListUseCaseModel.toUiModel()

        // Assert
        assertThat(uiModel.students.size).isEqualTo(2)
        assertThat(uiModel.students[0].firstName).isEqualTo("John")
        assertThat(uiModel.students[1].firstName).isEqualTo("Jane")
    }
}
