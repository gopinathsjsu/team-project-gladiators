package com.example.studentportal.common.service.models

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failureFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

fun <UseCaseModel : BaseUseCaseModel<UIModel>, Error, UIModel : BaseUiModel> successFlow(serviceModel: UseCaseModel): Flow<UseCaseResult<UseCaseModel, Error, UIModel>> {
    return flowOf(UseCaseResult.Success(serviceModel))
}

fun <UseCaseModel : BaseUseCaseModel<UiModel>, UiModel : BaseUiModel> defaultFailureFlow(
    code: Int,
    errorBody: ResponseBody
): Flow<UseCaseResult<UseCaseModel, DefaultError, UiModel>> {
    val message = try {
        JSONObject(errorBody.string()).getString("message")
    } catch (e: JSONException) {
        when (code) {
            401 -> "Unauthorized"
            403 -> "Forbidden"
            else -> "Parse Error"
        }
    }

    return flowOf(
        UseCaseResult.Failure(
            DefaultError(
                message
            )
        )
    )
}

fun <U : BaseUseCaseModel<UI>, UI : BaseUiModel> defaultFailureFlow(response: Response<*>): Flow<UseCaseResult<U, DefaultError, UI>> {
    return when (response.code()) {
        401 -> failureFlow(DefaultError("Parse error"))
        403 -> failureFlow(DefaultError("Parse error"))
        else -> failureFlow(DefaultError("Parse error"))
    }
}

fun <U : BaseUseCaseModel<UI>, UI : BaseUiModel> defaultFailureFlow(e: Exception): Flow<UseCaseResult<U, DefaultError, UI>> {
    return failureFlow(DefaultError(e.message ?: "Unknown Error"))
}
