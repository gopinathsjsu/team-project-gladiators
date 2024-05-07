package com.example.studentportal.course.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.VisibleForTesting

class UpdateCourseContentViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(SyllabusState.INITIAL)
    val uiResultLiveData: LiveData<SyllabusState>
        get() = _uiResultLiveData

    fun updateCourseContent(content: String) {
        val state = _uiResultLiveData.value ?: SyllabusState.INITIAL
        _uiResultLiveData.value = state.copy(content = content)
    }

    companion object {
        val UpdateCourseContentViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UpdateCourseContentViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

data class SyllabusState(
    val content: String = ""
) {
    companion object {
        val INITIAL = SyllabusState()
    }
}
