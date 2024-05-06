package com.example.studentportal.course.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.home.service.repository.CourseRepository
import com.example.studentportal.home.ui.model.CourseListUiModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
import kotlinx.coroutines.flow.Flow

class CreateCourseUseCase(
    override val repository: CourseRepository,
    val courseUseCaseModel: BaseCourseUseCaseModel.CourseUseCaseModel
) : BaseUseCase<CourseListUseCaseModel, DefaultError, CourseRepository, CourseListUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<CourseListUseCaseModel, DefaultError, CourseListUiModel>> {
        return try {
            val response = repository.createCourse(courseUseCaseModel)
            val courses = response.body()
            val errorResponse = response.errorBody()
            when {
                courses != null -> successFlow(courses)
                errorResponse != null -> defaultFailureFlow(code = response.code(), errorResponse)
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
