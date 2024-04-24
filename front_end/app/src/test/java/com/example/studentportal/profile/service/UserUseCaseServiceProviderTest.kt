package com.example.studentportal.profile.service

import com.example.studentportal.profile.service.repository.UserServiceProvider
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
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

class UserUseCaseServiceProviderTest {
    private lateinit var retrofit: Retrofit
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        userService = MockUserService()
        retrofit = mockk(relaxed = true) {
            every { create(UserService::class.java) } returns userService
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
        val provider = UserServiceProvider()
        assertThat(provider.retrofit).isEqualTo(this.retrofit)
        assertThat(provider.service()).isEqualTo(this.userService)
    }
}

class MockUserService : UserService {

    override fun getUser(userId: String): Call<UserUseCaseModel> {
        return mockk(relaxed = true)
    }
}
