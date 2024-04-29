package com.example.studentportal.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.assignment.ui.model.AssignmentListUiModel
import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.assignment.usecase.AssignmentListUseCase
import com.example.studentportal.assignment.usecase.CreateAssignmentUseCase
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class AssignmentsViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<AssignmentsUiResult>()
    val uiResultLiveData: LiveData<AssignmentsUiResult>
        get() = _uiResultLiveData

    suspend fun fetchAssignments(courseId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            AssignmentListUseCase(
                courseId = courseId,
                repository = koin.get()
            ).launch().collectLatest { result ->
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

    fun createNewAssignment(
        assignmentUiModel: AssignmentUiModel,
        onError: (BaseUiState<AssignmentListUiModel, DefaultError>) -> Unit
    ) {
        val previousAttachments = _uiResultLiveData.value
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            CreateAssignmentUseCase(
                repository = koin.get(),
                assignment = assignmentUiModel.toUseCaseModel()
            ).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        viewModelScope.launch {
                            // Post previous list
                            _uiResultLiveData.value = BaseUiState.Success(
                                AssignmentListUiModel(
                                    uiModels = previousAttachments.data()?.uiModels.orEmpty()
                                )
                            )
                            // Notify UI post failed
                            onError.invoke(result.failure())
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
        val AssignmentsViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AssignmentsViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias AssignmentsUiResult = BaseUiState<AssignmentListUiModel, DefaultError>
