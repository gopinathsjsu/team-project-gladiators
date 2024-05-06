package com.example.studentportal.home.service.repository

import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.course.usecase.model.CourseDetailsUseCaseModel
import com.example.studentportal.course.usecase.model.CourseInputUseCaseModel
import com.example.studentportal.home.service.CourseService
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class CourseRepository(
    override val provider: CourseServiceProvider
) : Repository<CourseService> {

    suspend fun fetchCourses(userId: String): Response<CourseListUseCaseModel> {
        val response = provider.service().fetchCourses(userId).execute()
        return if (response.isSuccessful) {
            Response.success(
                CourseListUseCaseModel(
                    useCaseModels = response.body().orEmpty()
                )
            )
        } else {
            val error = response.errorBody()
                ?: throw IllegalAccessException("Response does not return error")
            Response.error(response.code(), error)
        }
    }

    suspend fun createCourse(course: BaseCourseUseCaseModel.CourseUseCaseModel): Response<CourseListUseCaseModel> {
        val response = provider.service().createCourse(course).execute()
        return if (response.isSuccessful) {
            Response.success(
                CourseListUseCaseModel(
                    useCaseModels = response.body().orEmpty()
                )
            )
        } else {
            val error = response.errorBody()
                ?: throw IllegalAccessException("Response does not return error")
            Response.error(response.code(), error)
        }
    }

    suspend fun fetchCourseInputData(): Response<CourseInputUseCaseModel> {
        return provider.service().fetchCourseInputData().execute()
    }

    suspend fun fetchCourseDetails(courseId: String): Response<CourseDetailsUseCaseModel> {
        return provider.service().fetchCourseDetails(courseId).execute()
    }

    suspend fun updateCourse(course: BaseCourseUseCaseModel.CourseUseCaseModel): Response<CourseDetailsUseCaseModel> {
        return provider.service().updateCourse(courseUseCaseModel = course).execute()
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    CourseRepository(
                        provider = CourseServiceProvider()
                    )
                }
            }
        }
    }
}
