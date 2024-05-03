package com.example.studentportal.syllabus.ui.viewModel

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
import com.example.studentportal.syllabus.ui.model.SyllabusUiModel
import com.example.studentportal.syllabus.usecase.SyllabusAddUseCase
import com.example.studentportal.syllabus.usecase.SyllabusUpdateUseCase
import com.example.studentportal.syllabus.usecase.SyllabusUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class SyllabusViewModel(
    val dispatcher: CoroutineDispatcher
) : ViewModel() {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<SyllabusUiResult>()
    val uiResultLiveData: LiveData<SyllabusUiResult>
        get() = _uiResultLiveData

    private val _debugMessages = MutableLiveData<String>()
    val debugMessages: LiveData<String> = _debugMessages

    suspend fun fetchSyllabus(courseId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        _debugMessages.postValue("Loading syllabus for course ID: $courseId")
        viewModelScope.launch(dispatcher) {
            SyllabusUseCase(courseId = courseId, repository = koin.get())
                .launch()
                .collectLatest { result ->
                    when (result) {
                        is UseCaseResult.Failure -> {
                            viewModelScope.launch {
                                _debugMessages.postValue("Failed to load: ${result.error?.message}")
                                _uiResultLiveData.value = result.failure()
                            }
                        }
                        is UseCaseResult.Success -> {
                            viewModelScope.launch {
                                _debugMessages.postValue("Data loaded successfully")
                                _uiResultLiveData.value = result.success()
                            }
                        }
                    }
                }
        }
    }

    fun postSyllabus(courseId: String, description: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            SyllabusAddUseCase(courseId, description, repository = koin.get()).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        _uiResultLiveData.postValue(BaseUiState.Error(result.error ?: DefaultError("Unknown Error")))
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

    fun updateSyllabus(courseId: String, description: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            SyllabusUpdateUseCase(courseId = courseId, description = description, repository = koin.get()).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        _uiResultLiveData.postValue(BaseUiState.Error(result.error ?: DefaultError("Unknown error in updateSyllabus")))
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
        val SyllabusViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SyllabusViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias SyllabusUiResult = BaseUiState<SyllabusUiModel, DefaultError>
