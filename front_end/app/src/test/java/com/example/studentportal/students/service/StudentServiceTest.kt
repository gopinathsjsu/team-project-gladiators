package com.example.studentportal.students.service
import com.example.studentportal.students.usecase.model.StudentUseCaseModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import retrofit2.Call
import retrofit2.Response

class StudentServiceTest {
    private lateinit var studentService: StudentService // Make sure this is initialized properly
    private lateinit var mockCall: Call<List<StudentUseCaseModel>> // Ensure this is also initialized
    private var courseId = "course123"

    @Before
    fun setUp() {
        studentService = mockk() // Initialize the mock
        mockCall = mockk() // Initialize the mock call

        // Set up the mock behavior
        every { studentService.fetchStudents(courseId) } returns mockCall
        every { mockCall.execute() } returns Response.success(
            listOf(
                StudentUseCaseModel("1", "John", "Doe", "john.doe@example.com", "555-1234", "GRADUATE", null)
            )
        )
    }

    @After
    fun tearDown() {
        // Stop Koin after tests to clear all injections
        stopKoin()
    }

    @Test
    fun `test fetchStudents returns expected data`() {
        // This should now work as expected
        val response = studentService.fetchStudents(courseId).execute()

        // Assertions
        assert(response.isSuccessful)
        assert(response.body()?.size == 1)
        assert(response.body()?.get(0)?.firstName == "John")
    }
}
