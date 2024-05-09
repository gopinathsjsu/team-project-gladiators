package com.example.studentportal.profile.ui.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.areAnnoucementsDisabled
import com.example.studentportal.common.di.hideAnnouncements
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.usecase.UpdateUserProfileUseCase
import com.example.studentportal.profile.usecase.UserProfileUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class UserProfileViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<UserProfileUiResult>()
    val uiResultLiveData: LiveData<UserProfileUiResult>
        get() = _uiResultLiveData

    @VisibleForTesting
    internal val _checkedState = MutableLiveData<Boolean>(koin.get<SharedPreferences>().areAnnoucementsDisabled())
    val checkedState: LiveData<Boolean>
        get() = _checkedState

    fun updateChecked(checked: Boolean) {
        koin.get<SharedPreferences>().hideAnnouncements(checked)
        _checkedState.value = checked
    }

    suspend fun fetchUserData(userId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            UserProfileUseCase(userId = userId, repository = koin.get())
                .launch()
                .collectLatest { result ->
                    when (result) {
                        is UseCaseResult.Failure -> {
                            viewModelScope.launch {
                                _uiResultLiveData.value = result.failure()
                            }
                        }
                        is UseCaseResult.Success -> {
                            viewModelScope.launch {
                                _uiResultLiveData.value = result.success()
                            }
                        }
                    }
                }
        }
    }

    fun updateProfile(
        user: UserUiModel,
        onError: (BaseUiState<UserUiModel, DefaultError>) -> Unit
    ) {
        val previousData = uiResultLiveData.value.data()
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            UpdateUserProfileUseCase(
                user.toUseCaseModel(),
                repository = koin.get()
            ).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        _uiResultLiveData.value = BaseUiState.Success(
                            previousData ?: UserUiModel()
                        )
                        // Notify UI post failed
                        onError.invoke(result.failure())
                    }
                    is UseCaseResult.Success -> {
                        viewModelScope.launch {
                            _uiResultLiveData.value = result.success()
                        }
                    }
                }
            }
        }
    }

    companion object {
        val UserProfileViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserProfileViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias UserProfileUiResult = BaseUiState<UserUiModel, DefaultError>
