package com.example.studentportal.course.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.course.usecase.CourseDetailsUseCase
import com.example.studentportal.course.usecase.UpdateCourseUseCase
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher = dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<CourseDetailsUiResult>()
    val uiResultLiveData: LiveData<CourseDetailsUiResult>
        get() = _uiResultLiveData

    suspend fun fetchCourseDetails(courseId: String) {
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

    fun updateCourse(
        course: BaseCourseUiModel.CourseUiModel,
        onError: (BaseUiState<CourseDetailsUiModel, DefaultError>) -> Unit
    ) {
        val previousCourseDetails = _uiResultLiveData.value.data()
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            UpdateCourseUseCase(
                repository = koin.get(),
                course = course.toUseCaseModel()
            ).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        viewModelScope.launch {
                            // Post previous list
                            _uiResultLiveData.value = BaseUiState.Success(
                                CourseDetailsUiModel(
                                    previousCourseDetails?.courseUiModel,
                                    previousCourseDetails?.instructorUiModel
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
        val CourseViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CourseDetailsViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}
typealias CourseDetailsUiResult = BaseUiState<CourseDetailsUiModel, DefaultError>
