package com.example.studentportal.home.usecase

import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.service.repository.CourseRepository
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.example.studentportal.home.usecase.models.CourseListUseCaseModel
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
import java.util.Date

class CourseUseCaseTest {

    @Test
    fun `test student call success`() = runTest {
        // Arrange
        val userId = "UserId"
        val date = Date()
        val useCaseModel = CourseListUseCaseModel(
            useCaseModels = listOf(
                BaseCourseUseCaseModel.SemesterUseCaseModel(
                    id = "semesterId",
                    startDate = date,
                    endDate = date,
                    name = "Semester"
                ),
                BaseCourseUseCaseModel.CourseUseCaseModel(
                    id = "courseId",
                    instructor = "instructor",
                    enrolledStudents = setOf(),
                    assignments = setOf(),
                    semester = "semester",
                    published = false,
                    name = "CourseName",
                    description = "Description"
                )
            )
        )
        val repository: CourseRepository = mockk(relaxed = true) {
            coEvery { fetchCourses(userId) } returns Response.success(
                useCaseModel
            )
        }

        // Act
        val useCase = CoursesUseCase(
            userId = userId,
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.data).isEqualTo(useCaseModel)
        }
    }

    @Test
    fun `test student call error`() = runTest {
        // Arrange
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Backend Error"
        val userId = "UserId"
        val repository: CourseRepository = mockk(relaxed = true) {
            coEvery { fetchCourses(userId) } returns Response.error(
                400,
                mockk(relaxed = true) {
                    every { string() } returns "{\"message\":\"Backend Error\" }"
                }
            )
        }

        // Act
        val useCase = CoursesUseCase(
            userId = userId,
            repository = repository
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
    fun `test student call exception with message`() = runTest {
        // Arrange
        val userId = "UserId"
        val repository: CourseRepository = mockk(relaxed = true) {
            coEvery { fetchCourses(userId) } throws IllegalAccessException("Expected Message")
        }

        // Act
        val useCase = CoursesUseCase(
            userId = userId,
            repository = repository
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
    fun `test student call exception without message`() = runTest {
        // Arrange
        val userId = "UserId"
        val repository: CourseRepository = mockk(relaxed = true) {
            coEvery { fetchCourses(userId) } throws IllegalAccessException()
        }

        // Act
        val useCase = CoursesUseCase(
            userId = userId,
            repository = repository
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
    fun `test student call default`() = runTest {
        // Arrange
        val userId = "UserId"
        val repository: CourseRepository = mockk(relaxed = true) {
            coEvery { fetchCourses(userId) } returns mockk(relaxed = true) {
                every { body() } returns null
                every { errorBody() } returns null
            }
        }

        // Act
        val useCase = CoursesUseCase(
            userId = userId,
            repository = repository
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
