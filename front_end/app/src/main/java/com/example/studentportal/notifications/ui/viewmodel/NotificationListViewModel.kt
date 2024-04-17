package com.example.studentportal.notifications.ui.viewmodel

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
import com.example.studentportal.notifications.ui.model.NotificationListUiModel
import com.example.studentportal.notifications.usecase.NotificationListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationListViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<NotificationListUiResult>()
    val uiResultLiveData: LiveData<NotificationListUiResult>
        get() = _uiResultLiveData

    suspend fun fetchNotifications() {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            NotificationListUseCase(repository = koin.get())
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
        val NotificationListViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NotificationListViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias NotificationListUiResult = BaseUiState<NotificationListUiModel, DefaultError>
