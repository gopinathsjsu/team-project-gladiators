package com.example.studentportal.syllabus.service

import com.example.studentportal.syllabus.service.repository.SyllabusRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import retrofit2.Response

class SyllabusRepositoryTest {
    lateinit var service: SyllabusService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test fetchSyllabus call`() = runTest {
        // Arrange
        val courseId = "course123"
        coEvery { service.getCourse(courseId) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.fetchSyllabus(courseId)

        // Assert
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test updateDescription call`() = runTest {
        // Arrange
        val courseId = "course123"
        val description = "Updated Description"
        coEvery { service.updateDescription(courseId, description) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.updateDescription(courseId, description)

        // Assert
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test editDescription call`() = runTest {
        // Arrange
        val courseId = "course123"
        val description = "Edited Description"
        coEvery { service.editDescription(courseId, description) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.editDescription(courseId, description)

        // Assert
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test fetchSyllabus call, error`() = runTest {
        // Arrange
        val courseId = "course123"
        coEvery { service.getCourse(courseId) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.fetchSyllabus(courseId)

        // Assert
        assertThat(response.isSuccessful).isFalse()
    }

    @Test
    fun `test updateDescription call, error`() = runTest {
        // Arrange
        val courseId = "course123"
        val description = "Updated Description"
        coEvery { service.updateDescription(courseId, description) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.updateDescription(courseId, description)

        // Assert
        assertThat(response.isSuccessful).isFalse()
    }

    @Test
    fun `test editDescription call, error`() = runTest {
        // Arrange
        val courseId = "course123"
        val description = "Edited Description"
        coEvery { service.editDescription(courseId, description) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository = SyllabusRepository(
            mockk(relaxed = true) {
                every { service() } returns service
            }
        )

        // Act
        val response = repository.editDescription(courseId, description)

        // Assert
        assertThat(response.isSuccessful).isFalse()
    }
}
