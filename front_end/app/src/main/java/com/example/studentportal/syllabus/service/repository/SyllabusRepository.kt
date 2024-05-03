package com.example.studentportal.syllabus.service.repository

import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.syllabus.service.SyllabusService
import com.example.studentportal.syllabus.usecase.model.SyllabusUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class SyllabusRepository(
    override val provider: ServiceProvider<SyllabusService>
) : Repository<SyllabusService> {
    suspend fun fetchSyllabus(courseId: String): Response<SyllabusUseCaseModel> {
        return provider.service().getCourse(courseId).execute()
    }

    suspend fun updateDescription(courseId: String, description: String): Response<SyllabusUseCaseModel> {
        return provider.service().updateDescription(courseId, description).execute()
    }

    suspend fun editDescription(courseId: String, description: String): Response<SyllabusUseCaseModel> {
        return provider.service().editDescription(courseId, description).execute()
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    SyllabusRepository(
                        provider = SyllabusServiceProvider()
                    )
                }
            }
        }
    }
}
