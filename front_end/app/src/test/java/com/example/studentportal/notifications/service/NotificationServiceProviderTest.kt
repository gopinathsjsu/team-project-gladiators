package com.example.studentportal.notifications.service

import com.example.studentportal.notifications.service.repository.NotificationServiceProvider
import com.example.studentportal.notifications.usecase.model.NotificationUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit

class NotificationServiceProviderTest {
    private lateinit var retrofit: Retrofit
    private lateinit var notificationService: NotificationService

    @Before
    fun setUp() {
        notificationService = MockNotificationListService()
        retrofit = mockk(relaxed = true) {
            every { create(NotificationService::class.java) } returns notificationService
        }
        startKoin {
            modules(
                module {
                    single {
                        retrofit
                    }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test service provided`() {
        val provider = NotificationServiceProvider()
        assertThat(provider.retrofit).isEqualTo(this.retrofit)
        assertThat(provider.service()).isEqualTo(this.notificationService)
    }

    class MockNotificationListService : NotificationService {
        override fun fetchNotifications(): Call<List<NotificationUseCaseModel>> {
            return mockk(relaxed = true)
        }
    }
}
