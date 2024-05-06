package com.example.studentportal.home.ui.model

import android.os.Parcelable
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import kotlinx.parcelize.Parcelize
import java.util.Date

sealed class BaseCourseUiModel : BaseUiModel {

    @Parcelize
    data class CourseUiModel(
        val id: String,
        val instructor: String?,
        val enrolledStudents: Set<String>,
        val assignments: Set<String>,
        val semester: String,
        val isPublished: Boolean,
        val name: String,
        val description: String
    ) : BaseCourseUiModel(), Parcelable {
        fun toUseCaseModel(): BaseCourseUseCaseModel.CourseUseCaseModel {
            return BaseCourseUseCaseModel.CourseUseCaseModel(
                id = id,
                instructor = instructor,
                enrolledStudents = enrolledStudents,
                assignments = assignments,
                semester = semester,
                published = isPublished,
                name = name,
                description = description
            )
        }
    }

    data class FacultyUiModel(
        val id: String,
        val password: String,
        val firstName: String,
        val lastName: String
    ) : BaseCourseUiModel() {
        val fullName: String
            get() = "$firstName $lastName"
    }

    data class SemesterUiModel(
        val id: String,
        val startDate: Date,
        val endDate: Date,
        val name: String
    ) : BaseCourseUiModel()
}
