package com.example.studentportal.course.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.course.ui.model.CourseInputUiModel
import com.example.studentportal.course.usecase.model.CourseInputUseCaseModel
import com.example.studentportal.home.service.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class CourseInputDataUseCase(
    override val repository: CourseRepository
) : BaseUseCase<CourseInputUseCaseModel, DefaultError, CourseRepository, CourseInputUiModel> {
    override suspend fun launch(): Flow<UseCaseResult<CourseInputUseCaseModel, DefaultError, CourseInputUiModel>> {
        return try {
            val response = repository.fetchCourseInputData()
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
