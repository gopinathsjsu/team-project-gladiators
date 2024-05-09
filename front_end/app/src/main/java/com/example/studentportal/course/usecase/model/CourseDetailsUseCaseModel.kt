package com.example.studentportal.course.usecase.model

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel

data class CourseDetailsUseCaseModel(
    val course: BaseCourseUseCaseModel.CourseUseCaseModel,
    val instructor: BaseCourseUseCaseModel.FacultyUseCaseModel?,
    val announcements: List<AnnouncementUseCaseModel>
) : BaseUseCaseModel<CourseDetailsUiModel> {
    override fun toUiModel(): CourseDetailsUiModel {
        return CourseDetailsUiModel(
            courseUiModel = course.toBaseCourseUiModel() as BaseCourseUiModel.CourseUiModel,
            instructorUiModel = instructor?.toBaseCourseUiModel() as? BaseCourseUiModel.FacultyUiModel,
            announcements = announcements.map {
                it.toUiModel()
            }
        )
    }
}
