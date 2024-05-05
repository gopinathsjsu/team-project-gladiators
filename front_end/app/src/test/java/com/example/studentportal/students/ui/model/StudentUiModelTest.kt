package com.example.studentportal.students.ui.model

import com.example.studentportal.course.ui.model.UserType
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class StudentUiModelTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `StudentUiModel properties are correctly assigned`() {
        // Arrange
        val student = StudentUiModel(
            id = "1",
            firstName = "Alice",
            lastName = "Johnson",
            email = "alice.johnson@example.com",
            phone = "123-456-7890",
            type = UserType.STUDENT,
            biography = "Alice is a graduate student in Computer Science."
        )

        // Assert
        assertThat(student.id).isEqualTo("1")
        assertThat(student.firstName).isEqualTo("Alice")
        assertThat(student.lastName).isEqualTo("Johnson")
        assertThat(student.email).isEqualTo("alice.johnson@example.com")
        assertThat(student.phone).isEqualTo("123-456-7890")
        assertThat(student.type).isEqualTo(UserType.STUDENT)
        assertThat(student.biography).isEqualTo("Alice is a graduate student in Computer Science.")
    }

    @Test
    fun `StudentUiModel equality check`() {
        // Arrange
        val student1 = StudentUiModel(
            id = "1",
            firstName = "Alice",
            lastName = "Johnson",
            email = "alice.johnson@example.com",
            phone = "123-456-7890",
            type = UserType.STUDENT,
            biography = "Alice is a graduate student in Computer Science."
        )
        val student2 = StudentUiModel(
            id = "1",
            firstName = "Alice",
            lastName = "Johnson",
            email = "alice.johnson@example.com",
            phone = "123-456-7890",
            type = UserType.STUDENT,
            biography = "Alice is a graduate student in Computer Science."
        )

        // Assert
        assertThat(student1).isEqualTo(student2)
    }

    @Test
    fun `hashCode consistency check`() {
        // Arrange
        val student = StudentUiModel(
            id = "1",
            firstName = "Alice",
            lastName = "Johnson",
            email = "alice.johnson@example.com",
            phone = "123-456-7890",
            type = UserType.STUDENT,
            biography = "Alice is a graduate student in Computer Science."
        )

        // Act
        val model1 = student.hashCode()
        val model2 = student.hashCode()

        // Assert
        assertThat(model1).isEqualTo(model2)
    }

    @Test
    fun `toUseCaseModel converts correctly`() {
        // Arrange
        val uiModel = StudentUiModel(
            id = "1",
            firstName = "Alice",
            lastName = "Johnson",
            email = "alice@example.com",
            phone = "123-456-7890",
            type = UserType.STUDENT,
            biography = "Graduate student in CS."
        )

        // Act
        val useCaseModel = uiModel.toUseCaseModel()

        // Assert
        assertThat(useCaseModel.id).isEqualTo(uiModel.id)
        assertThat(useCaseModel.firstName).isEqualTo(uiModel.firstName)
        assertThat(useCaseModel.lastName).isEqualTo(uiModel.lastName)
        assertThat(useCaseModel.email).isEqualTo(uiModel.email)
        assertThat(useCaseModel.phone).isEqualTo(uiModel.phone)
        assertThat(useCaseModel.type).isEqualTo(uiModel.type.toString())
        assertThat(useCaseModel.biography).isEqualTo(uiModel.biography)
    }
}
