package com.example.studentportal.home.service.repository

import com.example.studentportal.home.service.StudentService
import com.example.studentportal.home.usecase.models.StudentUseCaseModel
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

class StudentServiceProviderTest {
    private lateinit var retrofit: Retrofit
    private lateinit var studentService: StudentService

    @Before
    fun setUp() {
        studentService = MockStudentService()
        retrofit = mockk(relaxed = true) {
            every { create(StudentService::class.java) } returns studentService
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
        val provider = StudentServiceProvider()
        assertThat(provider.retrofit).isEqualTo(this.retrofit)
        assertThat(provider.service()).isEqualTo(this.studentService)
    }

    class MockStudentService : StudentService {
        override fun fetchStudent(id: String): Call<StudentUseCaseModel> {
            return mockk(relaxed = true)
        }
    }
}
