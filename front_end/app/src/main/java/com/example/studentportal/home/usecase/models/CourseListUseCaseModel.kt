package com.example.studentportal.home.usecase.models

import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.home.ui.model.CourseListUiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CourseListUseCaseModel(
    private val useCaseModels: List<BaseCourseUseCaseModel>
) : BaseUseCaseModel<CourseListUiModel> {
    override fun toUiModel(): CourseListUiModel {
        return CourseListUiModel(
            uiModels = useCaseModels.map {
                it.toBaseCourseUiModel()
            }
        )
    }
}
