package com.example.studentportal.home.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiResult
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultUseCaseError
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

class UserViewModel(
    val userType: UserType,
    dispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher) {

    private val _uiResultLiveData = MutableLiveData<UserServiceUiResult>()
    val uiResultLiveData: LiveData<UserServiceUiResult>
        get() = _uiResultLiveData

    fun fetchStudent(userId: String) {
        _uiResultLiveData.value = BaseUiResult.Loading()
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
                UserViewModel(
                    userType = UserType.STUDENT,
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias UserServiceUiResult = BaseUiResult<UserUiModel, DefaultUseCaseError>