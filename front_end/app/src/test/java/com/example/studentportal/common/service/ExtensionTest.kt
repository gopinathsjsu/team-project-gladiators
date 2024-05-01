package com.example.studentportal.common.service

import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.common.usecase.DefaultError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test
import retrofit2.Response

class ExtensionTest {
    @Test
    fun `test successFlow`() = runTest {
        val serviceModel = TestUseCaseModel("ExpectedName")
        successFlow<TestUseCaseModel, DefaultError, TestUiModel>(
            serviceModel
        ).collectLatest { result ->
            assertThat(result.data).isEqualTo(serviceModel)
        }
    }

    @Test
    fun `test defaultFailureFlow, error body`() = runTest {
        // Mock Response Error
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Error Message"
        val mockResponseError = mockk<ResponseBody>(relaxed = true) {
            every { string() } returns "{ \"message\":\"Error Message\"}"
        }

        // Verify Error Returned
        defaultFailureFlow<TestUseCaseModel, TestUiModel>(code = 500, mockResponseError).collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultError("Error Message"))
        }

        // Clean Up Resources
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test defaultFailureFlow, parse error`() = runTest {
        // Mock Response Error
        mockkConstructor(JSONObject::class)
        every { anyConstructed<JSONObject>().getString("message") } returns "Error Message"
        val mockResponseError = mockk<ResponseBody>(relaxed = true) {
            every { string() } throws JSONException("ERROR")
        }

        // Verify Error Returned
        defaultFailureFlow<TestUseCaseModel, TestUiModel>(
            code = 500,
            mockResponseError
        ).collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultError("Parse Error"))
        }

        // Clean Up Resources
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test default defaultErrorFlow`() = runTest {
        defaultFailureFlow<TestUseCaseModel, TestUiModel>(
            Response.error<TestUiModel>(
                500,
                mockk(relaxed = true)
            )
        ).collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultError("Parse error"))
        }
    }

    data class TestUseCaseModel(val name: String = "Name") : BaseUseCaseModel<TestUiModel> {
        override fun toUiModel() = TestUiModel(name)
    }

    data class TestUiModel(val name: String = "Name") : BaseUiModel
}
