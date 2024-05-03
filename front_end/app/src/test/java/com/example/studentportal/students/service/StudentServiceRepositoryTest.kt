package com.example.studentportal.students.service

import com.example.studentportal.students.service.repository.StudentRepository
import com.example.studentportal.students.service.repository.StudentServiceProvider
import com.example.studentportal.students.usecase.model.StudentUseCaseModel
import com.google.common.truth.Truth
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
import org.koin.test.KoinTest
import retrofit2.Call
import retrofit2.Response

class StudentServiceRepositoryTest : KoinTest {
    private lateinit var service: StudentService
    private lateinit var callMock: Call<List<StudentUseCaseModel>>

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        callMock = mockk(relaxed = true)
        mockkConstructor(StudentServiceProvider::class)
        every { anyConstructed<StudentServiceProvider>().service() } returns service
        every { service.fetchStudents(any()) } returns callMock
        every { callMock.execute() } returns Response.success(listOf())

        startKoin {
            modules(StudentRepository.koinModule())
        }
    }

    @After
    fun tearDown() {
        unmockkConstructor(StudentServiceProvider::class)
        stopKoin()
    }

    @Test
    fun `test fetchStudents with valid courseId`() = runTest {
        val courseId = "course123"

        val repository: StudentRepository = getKoin().get()
        val response = repository.fetchStudents(courseId)

        verify {
            service.fetchStudents(courseId)
            callMock.execute()
        }
        Truth.assertThat(response.isSuccessful).isTrue()
        Truth.assertThat(response.body()?.students).isEmpty()
    }
}
