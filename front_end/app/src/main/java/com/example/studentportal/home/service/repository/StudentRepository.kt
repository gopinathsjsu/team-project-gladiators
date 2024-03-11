package com.example.studentportal.home.service.repository

import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.home.service.StudentService
import com.example.studentportal.home.service.models.StudentServiceModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class StudentRepository(
    override val provider: ServiceProvider<StudentService>
) : Repository<StudentService> {

    suspend fun fetchStudent(id: String): Response<StudentServiceModel>{
        return provider.service().fetchStudent(id).execute()
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    StudentRepository(
                        provider = StudentServiceProvider()
                    )
                }
            }
        }
    }
}