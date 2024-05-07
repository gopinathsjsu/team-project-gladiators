package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.di.koin
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
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

class GradeServiceRepositoryTest {

    lateinit var service: GradeService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        mockkConstructor(GradeServiceProvider::class)
        every {
            anyConstructed<GradeServiceProvider>().service()
        } returns service
        startKoin {
            modules(GradeRepository.koinModule())
        }
    }

    @After
    fun tearDown() {
        unmockkConstructor(GradeServiceProvider::class)
        stopKoin()
    }

    @Test
    fun `test fetchGradeList call`() = runTest {
        // Arrange
        every { service.fetchGradesByAssignment(any(), any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: GradeRepository = koin.get()
        val expectedAssignmentId = "assignmentId"
        val expectedUserId = "userId"

        // Act
        val response = repository.fetchGradesByAssignment(expectedAssignmentId, expectedUserId)

        // Assert
        verify {
            service.fetchGradesByAssignment(expectedAssignmentId, expectedUserId)
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test fetchGradeList call, error`() = runTest {
        // Arrange
        every { service.fetchGradesByAssignment(any(), any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository: GradeRepository = koin.get()
        val expectedAssignmentId = "assignmentId"
        val expectedUserId = "userId"

        // Act
        val response = repository.fetchGradesByAssignment(expectedAssignmentId, expectedUserId)

        // Assert
        verify {
            service.fetchGradesByAssignment(expectedAssignmentId, expectedUserId)
        }
        assertThat(response.isSuccessful).isFalse()
    }

    @Test
    fun `test updateGrade call`() = runTest {
        // Arrange
        every { service.updateGrade(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: GradeRepository = koin.get()
        val expectedGrade = GradeUseCaseModel(
            "1",
            50,
            "Arnold",
            "Smitt",
            "s123",
            "1",
            "www.com"
        )

        // Act
        val response = repository.updateGrade(expectedGrade)

        // Assert
        verify {
            service.updateGrade(expectedGrade)
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test updateGrade call, error`() = runTest {
        // Arrange
        every { service.updateGrade(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository: GradeRepository = koin.get()
        val expectedGrade = GradeUseCaseModel(
            "1",
            50,
            "Arnold",
            "Smitt",
            "s123",
            "1",
            "www.com"
        )

        // Act
        val response = repository.updateGrade(expectedGrade)

        // Assert
        verify {
            service.updateGrade(expectedGrade)
        }
        assertThat(response.isSuccessful).isFalse()
    }
}
