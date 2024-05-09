package com.example.studentportal.auth.ui.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.auth.usecase.LoginUseCase
import com.example.studentportal.auth.usecase.model.AuthRequest
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.di.saveAuthenticatedUserData
import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.profile.ui.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class AuthViewModel(
    dispatcher: CoroutineDispatcher,
    val sharedPreferences: SharedPreferences
) : BaseViewModel(dispatcher = dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData: MutableLiveData<AuthenticatedUserUiResult> = MutableLiveData(BaseUiState.Success<AuthUiState, DefaultError>(AuthUiState.INITIAL))
    val uiResultLiveData: LiveData<AuthenticatedUserUiResult>
        get() = _uiResultLiveData

    fun authenticate(onSuccessfulAuthentication: (user: UserUiModel) -> Unit) {
        val authRequest = _uiResultLiveData.value?.data?.toAuthRequest() ?: return
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            LoginUseCase(authRequest, repository = koin.get())
                .launch()
                .collectLatest { result ->
                    when (result) {
                        is UseCaseResult.Failure -> {
                            viewModelScope.launch {
                                val error = result.failure().error ?: DefaultError("Parse Error")
                                _uiResultLiveData.value = BaseUiState.Error(error = error)
                            }
                        }
                        is UseCaseResult.Success -> {
                            viewModelScope.launch {
                                _uiResultLiveData.value = BaseUiState.Success(AuthUiState.INITIAL)
                                result.data?.let {
                                    sharedPreferences.saveAuthenticatedUserData(it)
                                    onSuccessfulAuthentication.invoke(it.toUiModel())
                                }
                            }
                        }
                    }
                }
        }
    }

    fun updateUsername(username: String) {
        val state = _uiResultLiveData.value?.data ?: AuthUiState.INITIAL
        _uiResultLiveData.value = BaseUiState.Success(state.copy(usernameInput = username))
    }

    fun updatePassword(password: String) {
        val state = _uiResultLiveData.value?.data ?: AuthUiState.INITIAL
        _uiResultLiveData.value = BaseUiState.Success(state.copy(passwordInput = password))
    }

    fun updatePasswordVisible(isPasswordVisible: Boolean) {
        val state = _uiResultLiveData.value?.data ?: AuthUiState.INITIAL
        _uiResultLiveData.value = BaseUiState.Success(state.copy(passwordInputVisible = isPasswordVisible))
    }

    companion object {
        val AuthViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(
                    Dispatchers.IO,
                    koin.get()
                )
            }
        }
    }
}

data class AuthUiState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val passwordInputVisible: Boolean = false,
    val authenticatedUser: UserUiModel? = null
) : BaseUiModel {
    fun readyToSubmit(): Boolean {
        return usernameInput.isNotBlank() && passwordInput.isNotBlank()
    }

    fun toAuthRequest(): AuthRequest {
        return AuthRequest(
            email = usernameInput,
            password = passwordInput
        )
    }
    companion object {
        val INITIAL = AuthUiState()
    }
}

typealias AuthenticatedUserUiResult = BaseUiState<AuthUiState, DefaultError>
