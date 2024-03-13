package com.example.studentportal.common.usecase

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.ui.model.BaseUiResult
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
        assertThat(BaseUiResult.Loading<TestUiModel, DefaultUseCaseError>().isLoading()).isTrue()
    }

    @Test
    fun `test BaseUiResult isError`() {
        assertThat(
            BaseUiResult.Error<TestUiModel, DefaultUseCaseError>(
                DefaultUseCaseError("")
            ).isError()
        ).isTrue()
    }

    @Test
    fun `test BaseUiResult isSuccess`() {
        assertThat(
            BaseUiResult.Success<TestUiModel, DefaultUseCaseError>(
                TestUiModel()
            ).isSuccess()
        ).isTrue()
    }

    @Test
    fun `test useCase success`() {
        val data = TestUseCaseModel("WHATEVER")
        val success = UseCaseResult.Success<TestUseCaseModel, DefaultUseCaseError, TestUiModel>(data).success()
        assertThat(
            success.data()
        ).isEqualTo(TestUiModel("WHATEVER"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test useCase success on error`() {
        UseCaseResult.Failure<TestUseCaseModel, DefaultUseCaseError, TestUiModel>(
            DefaultUseCaseError("WHATEVER")
        ).success()
    }

    @Test
    fun `test useCase failure`() {
        val failure = UseCaseResult.Failure<TestUseCaseModel, DefaultUseCaseError, TestUiModel>(
            DefaultUseCaseError("WHATEVER")
        ).failure().error()
        assertThat(failure).isEqualTo(DefaultUseCaseError("WHATEVER"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test useCase error on success`() {
        val data = TestUseCaseModel("WHATEVER")
        UseCaseResult.Success<TestUseCaseModel, DefaultUseCaseError, TestUiModel>(data).failure()
    }

    data class TestUseCaseModel(val name: String = "Name") : BaseUseCaseModel<TestUiModel> {
        override fun toUiModel() = TestUiModel(name)
    }

    data class TestUiModel(val name: String = "Name") : BaseUiModel
}
