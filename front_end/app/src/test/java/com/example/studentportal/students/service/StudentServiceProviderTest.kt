package com.example.studentportal.students.service.repository

import com.example.studentportal.students.service.StudentService
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkConstructor
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import retrofit2.Retrofit
import kotlin.test.assertEquals

class StudentServiceProviderTest : AutoCloseKoinTest() {

    private lateinit var retrofitMock: Retrofit

    @Before
    fun setUp() {
        retrofitMock = mockk(relaxed = true)

        // Set up the mock for Retrofit.create() to return a mock StudentService
        every { retrofitMock.create(StudentService::class.java) } returns mockk<StudentService>(relaxed = true)
    }

    @After
    fun tearDown() {
        unmockkConstructor(StudentServiceProvider::class)
        stopKoin()
    }

    @Test
    fun `service method should create a StudentService instance from Retrofit`() {
        // Given
        val provider = StudentServiceProvider()

        // Set the Koin instance for testing
        startKoin {
            modules(
                module {
                    single { retrofitMock }
                }
            )
        }

        // When
        val createdService = provider.service()

        // Then
        val expectedService = retrofitMock.create(StudentService::class.java)
        assertEquals(expectedService, createdService)
    }

    @Test
    fun `retrofit getter should return the instance provided by Koin`() {
        // Given
        val provider = StudentServiceProvider()

        // Set the Koin instance for testing
        startKoin {
            modules(
                module {
                    single { retrofitMock }
                }
            )
        }

        // When
        val retrofitInstance = provider.retrofit

        // Then
        assertEquals(retrofitInstance, get<Retrofit>())
    }
}
