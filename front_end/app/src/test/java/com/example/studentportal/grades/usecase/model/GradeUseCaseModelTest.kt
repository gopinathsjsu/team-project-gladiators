package com.example.studentportal.grades.usecase.model

import com.example.studentportal.grades.ui.model.GradeListUiModel
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GradeUseCaseModelTest {

    @Test
    fun `test GradeList model`() {
        // Arrange
        val gradeListUseCaseModel = GradeListUseCaseModel(
            listOf(
                GradeUseCaseModel(
                    id = "1",
                    score = 10,
                    studentFirstName = "First-N1",
                    studentLastName = "Last-N1",
                    studentId = "1"
                )
            )
        )

        // Act
        val result = gradeListUseCaseModel.toUiModel()

        // Assert
        assertThat(result).isEqualTo(
            GradeListUiModel(
                listOf(
                    GradeUiModel(
                        id = "1",
                        score = 10,
                        studentFirstName = "First-N1",
                        studentLastName = "Last-N1",
                        studentId = "1"
                    )
                )
            )
        )
    }
}
