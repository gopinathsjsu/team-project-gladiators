package com.example.studentportal.course.ui.model

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.home.ui.model.BaseCourseUiModel

data class CourseDetailsUiModel(
    val courseUiModel: BaseCourseUiModel.CourseUiModel?,
    val instructorUiModel: BaseCourseUiModel.FacultyUiModel?,
    val announcements: List<AnnouncementUiModel>
) : BaseUiModel
