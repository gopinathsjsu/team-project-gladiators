package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit

class GradeServiceProviderTest {
    private lateinit var retrofit: Retrofit
    private lateinit var gradeService: GradeService

    @Before
    fun setUp() {
        gradeService = MockGradeListService()
        retrofit = mockk(relaxed = true) {
            every { create(GradeService::class.java) } returns gradeService
        }
        startKoin {
            modules(
                module {
                    single {
                        retrofit
                    }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test service provided`() {
        val provider = GradeServiceProvider()
        assertThat(provider.retrofit).isEqualTo(this.retrofit)
        assertThat(provider.service()).isEqualTo(this.gradeService)
    }

    class MockGradeListService : GradeService {
        override fun fetchGradesByAssignment(assignmentId: String, userId: String): Call<List<GradeUseCaseModel>> {
            return mockk(relaxed = true)
        }

        override fun updateGrade(gradeWithStudentName: GradeUseCaseModel): Call<GradeUseCaseModel> {
            return mockk(relaxed = true)
        }
    }
}
