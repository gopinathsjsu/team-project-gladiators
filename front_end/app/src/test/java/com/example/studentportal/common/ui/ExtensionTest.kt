package com.example.studentportal.common.ui

import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.ExtensionTests
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtensionTest {

    @Test
    fun `test useCase failure`() {
        val failure = UseCaseResult.Failure<ExtensionTests.TestUseCaseModel, DefaultError, ExtensionTests.TestUiModel>(
            DefaultError("WHATEVER")
        ).failure().error()
        assertThat(failure).isEqualTo(DefaultError("WHATEVER"))
    }

    @Test
    fun `test uiModel data`() {
        val expectedData = ExtensionTests.TestUiModel("WHATEVER")
        assertThat(
            BaseUiState.Success<ExtensionTests.TestUiModel, DefaultError>(
                expectedData
            ).data()
        ).isEqualTo(expectedData)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test uiModel data, wrong usage`() {
        val expectedData = ExtensionTests.TestUiModel("WHATEVER")
        BaseUiState.Success<ExtensionTests.TestUiModel, DefaultError>(
            expectedData
        ).error()
    }

    @Test
    fun `test uiModel error`() {
        val expectedError = DefaultError("WHATEVER")
        assertThat(
            BaseUiState.Error<ExtensionTests.TestUiModel, DefaultError>(
                expectedError
            ).error()
        ).isEqualTo(expectedError)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test uiModel error, wrong usage`() {
        val expectedError = DefaultError("WHATEVER")
        BaseUiState.Error<ExtensionTests.TestUiModel, DefaultError>(
            expectedError
        ).data()
    }
}
