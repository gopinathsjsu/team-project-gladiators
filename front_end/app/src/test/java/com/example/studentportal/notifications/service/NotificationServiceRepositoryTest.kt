package com.example.studentportal.notifications.service

import com.example.studentportal.common.di.koin
import com.example.studentportal.notifications.service.repository.NotificationRepository
import com.example.studentportal.notifications.service.repository.NotificationServiceProvider
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

class NotificationServiceRepositoryTest {
    lateinit var service: NotificationService

    @Before
    fun setUp() {
        service = mockk(relaxed = true)
        mockkConstructor(NotificationServiceProvider::class)
        every {
            anyConstructed<NotificationServiceProvider>().service()
        } returns service
        startKoin {
            modules(NotificationRepository.koinModule())
        }
    }

    @Test
    fun `test fetchNotificationList call`() = runTest {
        // Arrange
        val repository: NotificationRepository = koin.get()

        // Act
        repository.fetchNotifications()

        // Assert
        verify {
            service.fetchNotifications()
        }
    }

    @After
    fun tearDown() {
        unmockkConstructor(NotificationServiceProvider::class)
        stopKoin()
    }
}