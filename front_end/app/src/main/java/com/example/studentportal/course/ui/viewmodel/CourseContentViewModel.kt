package com.example.studentportal.course.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.course.usecase.CourseDetailsUseCase
import com.example.studentportal.course.usecase.UpdateCourseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class CourseContentViewModel(
    val dispatcher: CoroutineDispatcher
) : ViewModel() {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<CourseContentUiResult>()
    val uiResultLiveData: LiveData<CourseContentUiResult>
        get() = _uiResultLiveData

    suspend fun fetchSyllabus(courseId: String) {
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            CourseDetailsUseCase(courseId = courseId, repository = koin.get())
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

    fun updateContent(
        content: String,
        onError: (BaseUiState<CourseDetailsUiModel, DefaultError>) -> Unit
    ) {
        val previousCourseDetails = _uiResultLiveData.value.data() ?: return
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            val inputCourse = previousCourseDetails.courseUiModel?.copy(description = content) ?: return@launch
            UpdateCourseUseCase(
                repository = koin.get(),
                course = inputCourse.toUseCaseModel()
            ).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        viewModelScope.launch {
                            // Post previous list
                            _uiResultLiveData.value = BaseUiState.Success(
                                CourseDetailsUiModel(
                                    previousCourseDetails.courseUiModel,
                                    previousCourseDetails.instructorUiModel
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
        val SyllabusViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CourseContentViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias CourseContentUiResult = BaseUiState<CourseDetailsUiModel, DefaultError>
