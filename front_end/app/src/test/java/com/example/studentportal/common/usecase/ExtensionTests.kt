package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.model.isError
import com.example.studentportal.common.ui.model.isLoading
import com.example.studentportal.common.ui.model.isSuccess
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExtensionTests {

    @Test
    fun `test BaseUiResult isLoading`() {
        assertThat(BaseUiState.Loading<TestUiModel, DefaultError>().isLoading()).isTrue()
    }

    @Test
    fun `test BaseUiResult isError`() {
        assertThat(
            BaseUiState.Error<TestUiModel, DefaultError>(
                DefaultError("")
            ).isError()
        ).isTrue()
    }

    @Test
    fun `test BaseUiResult isSuccess`() {
        assertThat(
            BaseUiState.Success<TestUiModel, DefaultError>(
                TestUiModel()
            ).isSuccess()
        ).isTrue()
    }

    @Test
    fun `test useCase success`() {
        val data = TestUseCaseModel("WHATEVER")
        val success = UseCaseResult.Success<TestUseCaseModel, DefaultError, TestUiModel>(data).success()
        assertThat(
            success.data()
        ).isEqualTo(TestUiModel("WHATEVER"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test useCase success on error`() {
        UseCaseResult.Failure<TestUseCaseModel, DefaultError, TestUiModel>(
            DefaultError("WHATEVER")
        ).success()
    }

    @Test
    fun `test useCase failure`() {
        val failure = UseCaseResult.Failure<TestUseCaseModel, DefaultError, TestUiModel>(
            DefaultError("WHATEVER")
        ).failure().error()
        assertThat(failure).isEqualTo(DefaultError("WHATEVER"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test useCase error on success`() {
        val data = TestUseCaseModel("WHATEVER")
        UseCaseResult.Success<TestUseCaseModel, DefaultError, TestUiModel>(data).failure()
    }

    data class TestUseCaseModel(val name: String = "Name") : BaseUseCaseModel<TestUiModel> {
        override fun toUiModel() = TestUiModel(name)
    }

    data class TestUiModel(val name: String = "Name") : BaseUiModel
}
