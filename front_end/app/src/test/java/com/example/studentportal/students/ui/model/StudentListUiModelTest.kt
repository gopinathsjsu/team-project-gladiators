package com.example.studentportal.students.ui.model

import com.example.studentportal.course.ui.model.UserType
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class StudentListUiModelTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `StudentListUiModel stores and retrieves students correctly`() {
        // Arrange
        val students = listOf(
            StudentUiModel("1", "John", "Doe", "john@example.com", "555-1234", UserType.STUDENT, "Short bio."),
            StudentUiModel("2", "Jane", "Smith", "jane@example.com", null, UserType.STUDENT, "Another short bio.")
        )

        // Act
        val studentListUiModel = StudentListUiModel(students)

        // Assert
        assertThat(studentListUiModel.students).isEqualTo(students)
        assertThat(studentListUiModel.students.size).isEqualTo(2)
        assertThat(studentListUiModel.students[0].firstName).isEqualTo("John")
        assertThat(studentListUiModel.students[1].firstName).isEqualTo("Jane")
    }

    @Test
    fun `StudentListUiModel equality check`() {
        // Arrange
        val student1 = StudentUiModel("1", "John", "Doe", "john@example.com", "555-1234", UserType.STUDENT, "Short bio.")
        val student2 = StudentUiModel("1", "John", "Doe", "john@example.com", "555-1234", UserType.STUDENT, "Short bio.")

        // Act
        val model1 = StudentListUiModel(listOf(student1))
        val model2 = StudentListUiModel(listOf(student2))

        // Assert
        assertThat(model1).isEqualTo(model2)
    }

    @Test
    fun `hashCode consistency check`() {
        // Arrange
        val student = StudentUiModel("1", "John", "Doe", "john@example.com", "555-1234", UserType.STUDENT, "Short bio.")
        val model1 = StudentListUiModel(listOf(student))
        val model2 = StudentListUiModel(listOf(student))

        // Assert
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode())
    }
}
