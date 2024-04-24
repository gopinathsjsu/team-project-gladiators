package com.example.studentportal.profile.service

import com.example.studentportal.common.di.koin
import com.example.studentportal.notifications.service.repository.NotificationServiceProvider
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.profile.service.repository.UserServiceProvider
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

class UserServiceRepositoryTest {
    lateinit var service: UserService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        mockkConstructor(UserServiceProvider::class)
        every {
            anyConstructed<UserServiceProvider>().service()
        } returns service
        startKoin {
            modules(UserRepository.koinModule())
        }
    }

    @Test
    fun `test fetchNotificationList call`() = runTest {
        // Arrange
        every { service.getUser(any()) } returns mockk(relaxed = true) {
            every { execute() } returns Response.success(mockk(relaxed = true))
        }
        val repository: UserRepository = koin.get()

        // Act
        val response = repository.fetchUser("id")

        // Assert
        verify {
            service.getUser("id")
        }
        assertThat(response.isSuccessful).isTrue()
    }

    @After
    fun tearDown() {
        unmockkConstructor(NotificationServiceProvider::class)
        stopKoin()
    }
}
