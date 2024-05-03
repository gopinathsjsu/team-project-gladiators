package com.example.studentportal.syllabus.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.VisibleForTesting

class AddSyllabusViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(SyllabusState.INITIAL)
    val uiResultLiveData: LiveData<SyllabusState>
        get() = _uiResultLiveData

    fun updateSyllabus(syllabus: String) {
        val state = _uiResultLiveData.value ?: SyllabusState.INITIAL
        _uiResultLiveData.value = state.copy(syllabus = syllabus)
    }

    companion object {
        val AddSyllabusViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AddSyllabusViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

data class SyllabusState(
    public val syllabus: String = ""
) {
    fun readyToSubmit(): Boolean {
        return syllabus.isNotBlank()
    }
    companion object {
        val INITIAL = SyllabusState()
    }
}
