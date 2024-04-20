package com.example.studentportal.grades.ui.viewmodel

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
import com.example.studentportal.grades.ui.model.GradeListUiModel
import com.example.studentportal.grades.usecase.GradeListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GradeListViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<GradeListUiResult>()
    val uiResultLiveData: LiveData<GradeListUiResult>
        get() = _uiResultLiveData

    suspend fun fetchGrades(assignmentId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            GradeListUseCase(assignmentId = assignmentId, repository = koin.get())
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
        val GradeListViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GradeListViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias GradeListUiResult = BaseUiState<GradeListUiModel, DefaultError>
