package com.example.studentportal.profile.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.usecase.UserProfileUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class UserProfileViewModel(
    val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<UserProfileUiResult>()
    val uiResultLiveData: LiveData<UserProfileUiResult>
        get() = _uiResultLiveData

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
