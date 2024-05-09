package com.example.studentportal.grades.usecase

import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.grades.service.GradeRepository
import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import retrofit2.Response

class GradeListUseCaseTest {
    @Test
    fun `test grades call success`() = runTest {
        // Arrange
        val useCaseModel = GradeListUseCaseModel(
            listOf(
                GradeUseCaseModel(
                    id = "1",
                    score = 10,
                    studentFirstName = "First-N1",
                    studentLastName = "Last-N1",
                    studentId = "1",
                    submissionLink = null,
                    assignmentId = "1"
                )
            )
        )
        val repository: GradeRepository = mockk(relaxed = true) {
            coEvery { fetchGradesByAssignment(assignmentId = "", userId = "") } returns Response.success(
                useCaseModel
            )
        }

        // Act
        val useCase = GradeListUseCase(
            repository = repository,
            assignmentId = "",
            userId = ""
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.data).isEqualTo(
                useCaseModel
            )
        }
    }

    @Test
    fun `test grades call error`() = runTest {
        // Arrange
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Backend Error"
        val repository: GradeRepository = mockk(relaxed = true) {
            coEvery { fetchGradesByAssignment(assignmentId = "", userId = "") } returns Response.error(
                400,
                mockk(relaxed = true) {
                    every { string() } returns "{\"message\":\"Backend Error\" }"
                }
            )
        }

        // Act
        val useCase = GradeListUseCase(
            repository = repository,
            assignmentId = "",
            userId = ""
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Backend Error"
                )
            )
        }

        // Clear Resources
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test grades call exception with message`() = runTest {
        // Arrange
        val repository: GradeRepository = mockk(relaxed = true) {
            coEvery { fetchGradesByAssignment(assignmentId = "", userId = "") } throws IllegalAccessException("Expected Message")
        }

        // Act
        val useCase = GradeListUseCase(
            repository = repository,
            assignmentId = "",
            userId = ""
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Expected Message"
                )
            )
        }
    }

    @Test
    fun `test grades call exception without message`() = runTest {
        // Arrange
        val repository: GradeRepository = mockk(relaxed = true) {
            coEvery { fetchGradesByAssignment(assignmentId = "", userId = "") } throws IllegalAccessException()
        }

        // Act
        val useCase = GradeListUseCase(
            repository = repository,
            assignmentId = "",
            userId = ""
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Unknown Error"
                )
            )
        }
    }

    @Test
    fun `test grades call default`() = runTest {
        // Arrange
        val repository: GradeRepository = mockk(relaxed = true) {
            coEvery { fetchGradesByAssignment(assignmentId = "", userId = "") } returns mockk(relaxed = true) {
                every { body() } returns null
                every { errorBody() } returns null
            }
        }

        // Act
        val useCase = GradeListUseCase(
            repository = repository,
            assignmentId = "",
            userId = ""
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Parse error"
                )
            )
        }
    }
}
