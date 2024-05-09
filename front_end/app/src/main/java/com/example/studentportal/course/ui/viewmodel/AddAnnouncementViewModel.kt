package com.example.studentportal.course.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.course.ui.model.AnnouncementUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.VisibleForTesting
import java.util.UUID

class AddAnnouncementViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(UiState.INITIAL)
    val uiResultLiveData: LiveData<UiState>
        get() = _uiResultLiveData

    fun updateName(name: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(title = name)
    }

    fun updateDescription(link: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(description = link)
    }

    companion object {
        val AddAnnouncementsViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AddAnnouncementViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

data class UiState(
    val title: String = "",
    val description: String = ""
) {

    fun readyToSubmit(): Boolean {
        return title.isNotBlank() && description.isNotBlank()
    }

    fun toUiModel(courseId: String): AnnouncementUiModel {
        return AnnouncementUiModel(
            id = UUID.randomUUID().toString(),
            title = this.title,
            description = this.description,
            courseId = courseId
        )
    }
    companion object {
        val INITIAL = UiState()
    }
}
