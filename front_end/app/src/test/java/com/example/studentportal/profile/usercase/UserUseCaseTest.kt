package com.example.studentportal.profile.usercase

import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.profile.usecase.UserProfileUseCase
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import retrofit2.Response

class UserUseCaseTest {

    @Test
    fun `test user call success`() = runTest {
        // Arrange
        val useCaseModel = UserUseCaseModel(
            id = "id",
            password = "password",
            biography = "biography",
            email = "email",
            phone = "phone",
            firstName = "firstName",
            lastName = "lastName"
        )
        val repository: UserRepository = mockk(relaxed = true) {
            coEvery { fetchUser("id") } returns Response.success(
                useCaseModel
            )
        }

        // Act
        val useCase = UserProfileUseCase(
            userId = "id",
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.data).isEqualTo(
                useCaseModel
            )
        }
    }

    @Test
    fun `test notification call error`() = runTest {
        // Arrange
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Backend Error"
        val repository: UserRepository = mockk(relaxed = true) {
            coEvery { fetchUser("id") } returns Response.error(
                400,
                mockk(relaxed = true) {
                    every { string() } returns "{\"message\":\"Backend Error\" }"
                }
            )
        }

        // Act
        val useCase = UserProfileUseCase(
            userId = "id",
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Backend Error"
                )
            )
        }

        // Clear Resources
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test notification call exception with message`() = runTest {
        // Arrange
        val repository: UserRepository = mockk(relaxed = true) {
            coEvery { fetchUser("id") } throws IllegalAccessException("Expected Message")
        }

        // Act
        val useCase = UserProfileUseCase(
            userId = "id",
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Expected Message"
                )
            )
        }
    }

    @Test
    fun `test notification call exception without message`() = runTest {
        // Arrange
        val repository: UserRepository = mockk(relaxed = true) {
            coEvery { fetchUser("id") } throws IllegalAccessException()
        }

        // Act
        val useCase = UserProfileUseCase(
            userId = "id",
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            Truth.assertThat(it.error).isEqualTo(
                DefaultError(
                    "Unknown Error"
                )
            )
        }
    }

    @Test
    fun `test notification call default`() = runTest {
        // Arrange
        val repository: UserRepository = mockk(relaxed = true) {
            coEvery { fetchUser(any()) } returns mockk(relaxed = true) {
                every { body() } returns null
                every { errorBody() } returns null
            }
        }

        // Act
        val useCase = UserProfileUseCase(
            userId = "id",
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Parse error"
                )
            )
        }
    }
}
