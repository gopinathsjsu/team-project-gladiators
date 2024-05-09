package com.example.gradeportal.grades.service
import GradeService
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import retrofit2.Call
import retrofit2.Response

class GradeServiceTest {
    private lateinit var gradeService: GradeService // Make sure this is initialized properly
    private lateinit var mockCall: Call<List<GradeUseCaseModel>> // Ensure this is also initialized
    private var assignmentId = "assignment1"
    private var userId = "user1"
    private var sampleGrade = GradeUseCaseModel(
        id = "1",
        score = 10,
        studentFirstName = "First-N1",
        studentLastName = "Last-N1",
        studentId = "1",
        submissionLink = null,
        assignmentId = "1"
    )

    @Before
    fun setUp() {
        gradeService = mockk() // Initialize the mock
        mockCall = mockk() // Initialize the mock call

        // Set up the mock behavior
        every { gradeService.fetchGradesByAssignment(assignmentId, userId) } returns mockCall
        every { mockCall.execute() } returns Response.success(
            listOf(
                sampleGrade
            )
        )
    }

    @After
    fun tearDown() {
        // Stop Koin after tests to clear all injections
        stopKoin()
    }

    @Test
    fun `test fetchGrades returns expected data`() {
        // This should now work as expected
        val response = gradeService.fetchGradesByAssignment(assignmentId, userId).execute()

        // Assertions
        assert(response.isSuccessful)
        assert(response.body()?.size == 1)
        assert(response.body()?.get(0)?.studentFirstName == "First-N1")
    }
}
