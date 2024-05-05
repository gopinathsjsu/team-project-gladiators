package com.example.studentportal.students.usecase

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.BaseUseCase
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.students.service.repository.StudentRepository
import com.example.studentportal.students.ui.model.StudentListUiModel
import com.example.studentportal.students.usecase.model.StudentListUseCaseModel
import kotlinx.coroutines.flow.Flow

class StudentListUseCase(
    private val courseId: String,
    override val repository: StudentRepository
) : BaseUseCase<StudentListUseCaseModel, DefaultError, StudentRepository, StudentListUiModel> {

    override suspend fun launch(): Flow<UseCaseResult<StudentListUseCaseModel, DefaultError, StudentListUiModel>> {
        return try {
            val response = repository.fetchStudents(courseId)
            val students = response.body()
            val errorResponse = response.errorBody()
            when {
                students != null -> successFlow(students)
                errorResponse != null -> defaultFailureFlow(code = response.code(), errorResponse)
                else -> defaultFailureFlow(response)
            }
        } catch (e: Exception) {
            defaultFailureFlow(e)
        }
    }
}
