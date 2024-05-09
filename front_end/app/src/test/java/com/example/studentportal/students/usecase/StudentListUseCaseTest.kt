package com.example.studentportal.students.usecase.model

import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.students.service.repository.StudentRepository
import com.example.studentportal.students.usecase.StudentListUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.json.JSONObject
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin
import retrofit2.Response

class StudentListUseCaseTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test student call success`() = runTest {
        // Arrange
        val courseId = "course123"
        val studentModel = StudentListUseCaseModel(
            listOf(StudentUseCaseModel(id = "ID", firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = "12345", type = "UNDERGRADUATE", biography = "Yo, this is John Doe."))
        )
        val repository: StudentRepository = mockk(relaxed = true)
        coEvery { repository.fetchStudents(courseId) } returns Response.success(studentModel)

        // Act
        val useCase = StudentListUseCase(courseId, repository)
        val results = useCase.launch().toList()

        // Assert
        assertThat(results[0].data).isEqualTo(studentModel)
        assertThat(results).hasSize(1) // Ensure only one emission occurred
    }

    @Test
    fun `test student call error`() = runTest {
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Backend Error"

        // Arrange
        val courseId = "course123"
        val repository: StudentRepository = mockk(relaxed = true)

        val errorBody = mockk<ResponseBody>(relaxed = true) {
            every { string() } returns "{\"message\":\"Backend Error\"}"
        }
        coEvery { repository.fetchStudents(courseId) } returns Response.error(400, errorBody)

        // Act
        val useCase = StudentListUseCase(courseId, repository)
        val results = useCase.launch().toList()

        // Assert
        assertThat(results[0].error?.message).isEqualTo("Backend Error")
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test student call exception`() = runTest {
        // Arrange
        val courseId = "course123"
        val repository: StudentRepository = mockk(relaxed = true)
        coEvery { repository.fetchStudents(courseId) } throws Exception("Network failure")

        // Act
        val useCase = StudentListUseCase(courseId, repository)
        val results = useCase.launch().toList()

        // Assert
        assertThat(results[0].error).isEqualTo(DefaultError("Network failure"))
    }
}
