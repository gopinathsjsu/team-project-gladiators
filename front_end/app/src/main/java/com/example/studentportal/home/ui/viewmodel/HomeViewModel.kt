package com.example.studentportal.home.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.home.usecase.StudentUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    val userType: UserType,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<UserUiResult>()
    val uiResultLiveData: LiveData<UserUiResult>
        get() = _uiResultLiveData

    fun fetchStudent(userId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            StudentUseCase(userId = userId, repository = koin.get())
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
        val StudentViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    userType = UserType.STUDENT,
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias UserUiResult = BaseUiState<UserUiModel, DefaultError>