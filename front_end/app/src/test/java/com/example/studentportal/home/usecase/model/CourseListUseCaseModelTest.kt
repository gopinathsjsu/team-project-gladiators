package com.example.studentportal.home.usecase.model

import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.Date

class CourseListUseCaseModelTest {

    @Test
    fun `test CourseListUseCaseModel`() {
        // Arrange
        val date = Date()
        val useCaseModel = CourseListUseCaseModel(
            useCaseModels = listOf(
                BaseCourseUseCaseModel.SemesterUseCaseModel(
                    id = "semesterId",
                    startDate = date,
                    endDate = date,
                    name = "Semester"
                ),
                BaseCourseUseCaseModel.CourseUseCaseModel(
                    id = "courseId",
                    instructor = "instructor",
                    enrolledStudents = setOf(),
                    assignments = setOf(),
                    semester = "semester",
                    published = false,
                    name = "CourseName",
                    description = "Description"
                )
            )
        )

        // Act
        val result = useCaseModel.toUiModel()

        // Assert
        val models = result.uiModels
        assertThat(models.size).isEqualTo(2)
        assertThat(models[0]).isEqualTo(
            BaseCourseUiModel.SemesterUiModel(
                id = "semesterId",
                startDate = date,
                endDate = date,
                name = "Semester"
            )
        )
        assertThat(models[1]).isEqualTo(
            BaseCourseUiModel.CourseUiModel(
                id = "courseId",
                instructor = "instructor",
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = "semester",
                isPublished = false,
                name = "CourseName",
                description = "Description"
            )
        )
    }
}
