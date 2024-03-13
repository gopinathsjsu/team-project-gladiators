package com.example.studentportal.home.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.home.service.StudentService
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

class StudentServiceRepositoryTest {

    lateinit var service: StudentService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        mockkConstructor(StudentServiceProvider::class)
        every {
            anyConstructed<StudentServiceProvider>().service()
        } returns service
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
    fun `test fetchStudent call`() = runTest {
        // Arrange
        val repository: StudentRepository = koin.get()
        val expectedId = "expectedId"

        // Act
        repository.fetchStudent(expectedId)

        // Assert
        verify {
            service.fetchStudent(expectedId)
        }
    }
}
