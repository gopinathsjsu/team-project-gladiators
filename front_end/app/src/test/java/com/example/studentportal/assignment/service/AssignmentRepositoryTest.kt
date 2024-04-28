package com.example.studentportal.assignment.service

import com.example.studentportal.assignment.service.repository.AssignmentRepository
import com.example.studentportal.assignment.usecase.models.AssignmentUseCaseModel
import com.example.studentportal.common.di.koin
import com.example.studentportal.home.service.repository.CourseRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AssignmentRepositoryTest {

    lateinit var service: AssignmentService

    @Before
    fun setUp(){
        service = mockk(relaxed = true)
    }

    @Test
    fun `test fetchAssignments call`() = runTest {
        // Arrange
        every { service.fetchAssignments(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val expectedId = "expectedId"

        // Act
        val response = repository.fetchAssignments(expectedId)

        // Assert
        verify {
            service.fetchAssignments(expectedId)
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test createAssignments call`() = runTest {
        // Arrange
        every { service.postAssignment(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val assignment: AssignmentUseCaseModel = mockk(relaxed = true)

        // Act
        val response = repository.createNewAssignment(assignment)

        // Assert
        verify {
            service.postAssignment(assignment)
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `test fetchAssignments call, error`() = runTest {
        // Arrange
        every { service.fetchAssignments(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val expectedId = "expectedId"

        // Act
        val response = repository.fetchAssignments(expectedId)

        // Assert
        verify {
            service.fetchAssignments(expectedId)
        }
        assertThat(response.isSuccessful).isFalse()
    }

    @Test(expected = IllegalAccessException::class)
    fun `test fetchAssignments call, error expect exception`() = runTest {
        // Arrange
        every { service.fetchAssignments(any()) } returns mockk(relaxed = true) {
            every { execute() } returns mockk(relaxed = true) {
                every { isSuccessful } returns false
                every { errorBody() } returns null
            }
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val expectedId = "expectedId"

        // Act
        repository.fetchAssignments(expectedId)
    }

    @Test
    fun `test create call, error`() = runTest {
        // Arrange
        every { service.postAssignment(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.error(400, mockk(relaxed = true))
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val assignment: AssignmentUseCaseModel = mockk(relaxed = true)

        // Act
        val response = repository.createNewAssignment(assignment)

        // Assert
        verify {
            service.postAssignment(assignment = assignment)
        }
        assertThat(response.isSuccessful).isFalse()
    }

    @Test(expected = IllegalAccessException::class)
    fun `test createAssignment call, error expect exception`() = runTest {
        // Arrange
        every { service.postAssignment(any()) } returns mockk(relaxed = true) {
            every { execute() } returns mockk(relaxed = true) {
                every { isSuccessful } returns false
                every { errorBody() } returns null
            }
        }
        val repository: AssignmentRepository = AssignmentRepository(
            mockk(relaxed = true){
                every { service() } returns service
            }
        )
        val assignment: AssignmentUseCaseModel = mockk(relaxed = true)

        // Act
        repository.createNewAssignment(assignment)
    }
}