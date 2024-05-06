package com.example.studentportal.course.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.CourseInputUiModel
import com.example.studentportal.profile.usecase.model.UserUseCaseModel

data class CourseInputUseCaseModel(
    val semesters: List<SemesterUseCaseModel>,
    val users: List<UserUseCaseModel>
) : BaseUseCaseModel<CourseInputUiModel> {
    override fun toUiModel(): CourseInputUiModel {
        return CourseInputUiModel(
            semesters = semesters.map { it.toUiModel() },
            instructors = users.map { it.toUiModel() }
        )
    }
}
