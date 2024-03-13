package com.example.studentportal.common.service

import com.example.studentportal.common.service.models.BaseServiceModel
import com.example.studentportal.common.service.models.defaultFailureFlow
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.common.usecase.DefaultUseCaseError
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

class ExtensionTest {
    @Test
    fun `test successFlow`() = runTest {
        val serviceModel = TestServiceModel("ExpectedName")
        successFlow<TestUseCaseModel, DefaultUseCaseError, TestUiModel>(
            serviceModel
        ).collectLatest { result ->
            assertThat(result.data).isEqualTo(serviceModel.toUseCaseModel())
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
        defaultFailureFlow<TestUseCaseModel, TestUiModel>(mockResponseError).collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultUseCaseError("Error Message"))
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
        defaultFailureFlow<TestUseCaseModel, TestUiModel>(mockResponseError).collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultUseCaseError("Parse Error"))
        }

        // Clean Up Resources
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun `test default defaultErrorFlow`() = runTest {
        defaultFailureFlow<TestUseCaseModel, TestUiModel>().collectLatest { result ->
            assertThat(result.error).isEqualTo(DefaultUseCaseError("Parse error"))
        }
    }

    data class TestServiceModel(val name: String = "Name") : BaseServiceModel<TestUseCaseModel, TestUiModel> {
        override fun toUseCaseModel(): TestUseCaseModel {
            return TestUseCaseModel(name)
        }
    }
    data class TestUseCaseModel(val name: String = "Name") : BaseUseCaseModel<TestUiModel> {
        override fun toUiModel() = TestUiModel(name)
    }

    data class TestUiModel(val name: String = "Name") : BaseUiModel
}
