package com.example.studentportal.common.ui

import com.example.studentportal.common.ui.model.BaseUiResult
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.usecase.DefaultUseCaseError
import com.example.studentportal.common.usecase.ExtensionTests
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtensionTest {

    @Test
    fun `test useCase failure`() {
        val failure = UseCaseResult.Failure<ExtensionTests.TestUseCaseModel, DefaultUseCaseError, ExtensionTests.TestUiModel>(
            DefaultUseCaseError("WHATEVER")
        ).failure().error()
        assertThat(failure).isEqualTo(DefaultUseCaseError("WHATEVER"))
    }

    @Test
    fun `test uiModel data`() {
        val expectedData = ExtensionTests.TestUiModel("WHATEVER")
        assertThat(
            BaseUiResult.Success<ExtensionTests.TestUiModel, DefaultUseCaseError>(
                expectedData
            ).data()
        ).isEqualTo(expectedData)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test uiModel data, wrong usage`() {
        val expectedData = ExtensionTests.TestUiModel("WHATEVER")
        BaseUiResult.Success<ExtensionTests.TestUiModel, DefaultUseCaseError>(
            expectedData
        ).error()
    }

    @Test
    fun `test uiModel error`() {
        val expectedError = DefaultUseCaseError("WHATEVER")
        assertThat(
            BaseUiResult.Error<ExtensionTests.TestUiModel, DefaultUseCaseError>(
                expectedError
            ).error()
        ).isEqualTo(expectedError)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test uiModel error, wrong usage`() {
        val expectedError = DefaultUseCaseError("WHATEVER")
        BaseUiResult.Error<ExtensionTests.TestUiModel, DefaultUseCaseError>(
            expectedError
        ).data()
    }
}
