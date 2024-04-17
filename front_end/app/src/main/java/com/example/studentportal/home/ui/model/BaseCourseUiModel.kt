package com.example.studentportal.home.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel
import java.util.Date

sealed class BaseCourseUiModel : BaseUiModel {
    data class CourseUiModel(
        val id: String,
        val instructor: String?,
        val enrolledStudents: Set<String>,
        val assignments: Set<String>,
        val semester: String,
        val isPublished: Boolean,
        val name: String,
        val description: String
    ) : BaseCourseUiModel()

    data class FacultyUiModel(
        val id: String,
        val password: String,
        val firstName: String,
        val lastName: String
    ) : BaseCourseUiModel()

    data class SemesterUiModel(
        val id: String,
        val startDate: Date,
        val endDate: Date,
        val name: String
    ) : BaseCourseUiModel()
}
