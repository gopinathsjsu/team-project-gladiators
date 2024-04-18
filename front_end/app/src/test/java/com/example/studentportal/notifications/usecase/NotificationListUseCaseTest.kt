package com.example.studentportal.notifications.usecase

import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.notifications.service.repository.NotificationRepository
import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import com.example.studentportal.notifications.usecase.model.NotificationUseCaseModel
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

class NotificationListUseCaseTest {
    @Test
    fun `test student call success`() = runTest {
        // Arrange
        val useCaseModel = NotificationListUseCaseModel(
            listOf(
                NotificationUseCaseModel(
                    id = "ID",
                    courseName = "COURSE_NAME",
                    eventTitle = "EVENT_TITLE",
                    eventId = "EVENT_ID",
                    type = "ASSIGNMENT_GRADED"
                )
            )
        )
        val repository: NotificationRepository = mockk(relaxed = true) {
            coEvery { fetchNotifications() } returns Response.success(
                useCaseModel
            )
        }

        // Act
        val useCase = NotificationListUseCase(
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
    fun `test student call error`() = runTest {
        // Arrange
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Backend Error"
        val repository: NotificationRepository = mockk(relaxed = true) {
            coEvery { fetchNotifications() } returns Response.error(
                400,
                mockk(relaxed = true) {
                    every { string() } returns "{\"message\":\"Backend Error\" }"
                }
            )
        }

        // Act
        val useCase = NotificationListUseCase(
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
    fun `test student call exception with message`() = runTest {
        // Arrange
        val repository: NotificationRepository = mockk(relaxed = true) {
            coEvery { fetchNotifications() } throws IllegalAccessException("Expected Message")
        }

        // Act
        val useCase = NotificationListUseCase(
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
    fun `test student call exception without message`() = runTest {
        // Arrange
        val repository: NotificationRepository = mockk(relaxed = true) {
            coEvery { fetchNotifications() } throws IllegalAccessException()
        }

        // Act
        val useCase = NotificationListUseCase(
            repository = repository
        )
        val result = useCase.launch()

        // Assert
        result.collectLatest {
            assertThat(it.error).isEqualTo(
                DefaultError(
                    "Unknown Error"
                )
            )
        }
    }

    @Test
    fun `test student call default`() = runTest {
        // Arrange
        val repository: NotificationRepository = mockk(relaxed = true) {
            coEvery { fetchNotifications() } returns mockk(relaxed = true) {
                every { body() } returns null
                every { errorBody() } returns null
            }
        }

        // Act
        val useCase = NotificationListUseCase(
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
