package com.example.studentportal.course.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.profile.ui.model.UserUiModel

data class CourseInputUiModel(
    val semesters: List<SemesterUiModel>,
    val instructors: List<UserUiModel>
) : BaseUiModel {

    fun defaultSelectedInstructor(instructorId: String?): UserUiModel? {
        return if (instructors.isNotEmpty()) {
            instructors.firstOrNull { it.id == instructorId } ?: instructors[0]
        } else {
            null
        }
    }

    fun defaultSelectedSemester(semesterId: String?): SemesterUiModel? {
        return if (semesters.isNotEmpty()) {
            semesters.firstOrNull { it.id == semesterId } ?: semesters[0]
        } else {
            null
        }
    }
}
