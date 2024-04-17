package com.example.studentportal.home.service.repository

import com.example.studentportal.home.service.CourseService
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
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

class CourseServiceProviderTest {
    private lateinit var retrofit: Retrofit
    private lateinit var courseService: CourseService

    @Before
    fun setUp() {
        courseService = MockCoursesService()
        retrofit = mockk(relaxed = true) {
            every { create(CourseService::class.java) } returns courseService
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
        val provider = CourseServiceProvider()
        assertThat(provider.retrofit).isEqualTo(this.retrofit)
        assertThat(provider.service()).isEqualTo(this.courseService)
    }

    class MockCoursesService : CourseService {
        override fun fetchCourses(userId: String): Call<List<BaseCourseUseCaseModel>> {
            return mockk(relaxed = true)
        }
    }
}
