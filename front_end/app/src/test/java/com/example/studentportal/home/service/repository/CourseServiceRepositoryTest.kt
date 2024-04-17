package com.example.studentportal.home.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.home.service.CourseService
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import retrofit2.Response

class CourseServiceRepositoryTest {

    lateinit var service: CourseService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        mockkConstructor(CourseServiceProvider::class)
        every {
            anyConstructed<CourseServiceProvider>().service()
        } returns service
        startKoin {
            modules(CourseRepository.koinModule())
        }
    }

    @After
    fun tearDown() {
        unmockkConstructor(CourseServiceProvider::class)
        stopKoin()
    }

    @Test
    fun `test fetchCourse call`() = runTest {
        // Arrange
        every { service.fetchCourses(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: CourseRepository = koin.get()
        val expectedId = "expectedId"

        // Act
        val response = repository.fetchCourses(expectedId)

        // Assert
        verify {
            service.fetchCourses(expectedId)
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test fetchCourse call, error`() = runTest {
        // Arrange
        every { service.fetchCourses(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository: CourseRepository = koin.get()
        val expectedId = "expectedId"

        // Act
        val response = repository.fetchCourses(expectedId)

        // Assert
        verify {
            service.fetchCourses(expectedId)
        }
        assertThat(response.isSuccessful).isFalse()
    }

    @Test(expected = IllegalAccessException::class)
    fun `test fetchCourse call, error expect exception`() = runTest {
        // Arrange
        every { service.fetchCourses(any()) } returns mockk(relaxed = true) {
            every { execute() } returns mockk(relaxed = true) {
                every { isSuccessful } returns false
                every { errorBody() } returns null
            }
        }
        val repository: CourseRepository = koin.get()
        val expectedId = "expectedId"

        // Act
        repository.fetchCourses(expectedId)
    }
}
