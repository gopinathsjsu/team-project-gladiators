package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class GradeRepository(
    private val service: GradeService
) {

    suspend fun fetchGradesByAssignment(assignmentId: String): Response<GradeListUseCaseModel> {
        return service.fetchGradesByAssignment(assignmentId).execute()
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    GradeRepository(
                        service = get()
                    )
                }
            }
        }
    }
}
