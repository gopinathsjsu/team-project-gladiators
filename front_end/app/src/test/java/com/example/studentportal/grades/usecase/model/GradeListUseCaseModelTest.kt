package com.example.gradeportal.grades.usecase.model

import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class GradeListUseCaseModelTest {
    private var sampleGrade = GradeUseCaseModel(
        id = "1",
        score = 10,
        studentFirstName = null,
        studentLastName = "Last-N1",
        studentId = "1",
        submissionLink = null,
        assignmentId = "1"
    )

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `toUiModel correctly transforms list of GradeUseCaseModel to GradeListUiModel`() {
        // Arrange
        val gradeUseCaseModels = listOf(
            sampleGrade
        )
        val gradeListUseCaseModel = GradeListUseCaseModel(
            grades = gradeUseCaseModels
        )

        // Act
        val uiModel = gradeListUseCaseModel.toUiModel()

        // Assert
        assertThat(uiModel.grades.size).isEqualTo(1)
        assertThat(uiModel.grades[0].studentFirstName).isEqualTo("")
        assertThat(uiModel.grades[0].studentLastName).isEqualTo("Last-N1")
    }
}
