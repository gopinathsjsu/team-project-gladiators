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
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.common.usecase.failure
import com.example.studentportal.common.usecase.success
import com.example.studentportal.course.usecase.CreateCourseUseCase
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.ui.model.CourseListUiModel
import com.example.studentportal.home.usecase.CoursesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData<CourseListUiResult>()
    val uiResultLiveData: LiveData<CourseListUiResult>
        get() = _uiResultLiveData

    suspend fun fetchCourses(userId: String?) {
        userId?.let {
            _uiResultLiveData.value = BaseUiState.Loading()
            viewModelScope.launch(dispatcher) {
                CoursesUseCase(userId = userId, repository = koin.get())
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
    }

    fun createNewCourse(
        course: BaseCourseUiModel.CourseUiModel,
        onError: (BaseUiState<CourseListUiModel, DefaultError>) -> Unit
    ) {
        val previousCourses = _uiResultLiveData.value
        _uiResultLiveData.value = BaseUiState.Loading()
        viewModelScope.launch(dispatcher) {
            CreateCourseUseCase(
                repository = koin.get(),
                courseUseCaseModel = course.toUseCaseModel()
            ).launch().collectLatest { result ->
                when (result) {
                    is UseCaseResult.Failure -> {
                        viewModelScope.launch {
                            // Post previous list
                            _uiResultLiveData.value = BaseUiState.Success(
                                CourseListUiModel(
                                    uiModels = previousCourses.data()?.uiModels.orEmpty()
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
        val HomeViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

typealias CourseListUiResult = BaseUiState<CourseListUiModel, DefaultError>
