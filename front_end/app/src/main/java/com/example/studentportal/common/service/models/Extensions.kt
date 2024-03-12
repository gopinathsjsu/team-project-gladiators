package com.example.studentportal.common.service.models

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.common.usecase.DefaultUseCaseError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failureFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

fun <UseCaseModel : BaseUseCaseModel<UIModel>, Error, UIModel : BaseUiModel> successFlow(serviceModel: BaseServiceModel<UseCaseModel, UIModel>): Flow<UseCaseResult<UseCaseModel, Error, UIModel>> {
    return flowOf(UseCaseResult.Success(serviceModel.toUseCaseModel()))
}

fun <UseCaseModel : BaseUseCaseModel<UiModel>, UiModel : BaseUiModel> defaultFailureFlow(errorBody: ResponseBody): Flow<UseCaseResult<UseCaseModel, DefaultUseCaseError, UiModel>> {
    val message = try {
        JSONObject(errorBody.string()).getString("message")
    } catch (e: JSONException) {
        "Parse Error"
    }

    return flowOf(
        UseCaseResult.Failure(
            DefaultUseCaseError(
                message
            )
        )
    )
}

fun <U : BaseUseCaseModel<UI>, UI : BaseUiModel> defaultFailureFlow(): Flow<UseCaseResult<U, DefaultUseCaseError, UI>> {
    return failureFlow(DefaultUseCaseError("Parse error"))
}
