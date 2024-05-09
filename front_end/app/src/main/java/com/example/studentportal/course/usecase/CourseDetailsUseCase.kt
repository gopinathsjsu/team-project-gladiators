package com.example.studentportal.course.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.course.usecase.model.CourseDetailsUseCaseModel
import com.example.studentportal.home.service.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class CourseDetailsUseCase(
    val courseId: String,
    override val repository: CourseRepository
) : BaseUseCase<CourseDetailsUseCaseModel, DefaultError, CourseRepository, CourseDetailsUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<CourseDetailsUseCaseModel, DefaultError, CourseDetailsUiModel>> {
        return try {
            val response = repository.fetchCourseDetails(courseId)
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
