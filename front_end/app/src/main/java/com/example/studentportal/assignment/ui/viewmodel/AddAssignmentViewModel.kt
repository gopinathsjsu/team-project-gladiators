package com.example.studentportal.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.VisibleForTesting
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.UUID

class AddAssignmentViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(UiState.INITIAL)
    val uiResultLiveData: LiveData<UiState>
        get() = _uiResultLiveData

    fun updateDate(date: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(dueDate = date)
    }

    fun updateName(name: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(name = name)
    }

    fun updateDescription(link: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(link = link)
    }

    companion object {
        val AddAssignmentsViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AddAssignmentViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}

data class UiState(
    val dueDate: String = "",
    val name: String = "",
    val link: String = ""
) {
    fun validDate(): Date? {
        return try {
            val modifiedDueDate = if (dueDate.length == 8) {
                "${dueDate.substring(0, 2)}-${dueDate.substring(2, 4)}-${dueDate.substring(4, 8)}"
            } else {
                dueDate
            }
            val localDate = LocalDate.parse(modifiedDueDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"))
            return Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC))
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun readyToSubmit(): Boolean {
        return name.isNotBlank() && link.isNotBlank() && validDate() != null
    }

    fun toUiModel(courseId: String): AssignmentUiModel {
        return AssignmentUiModel(
            id = UUID.randomUUID().toString(),
            name = this.name,
            link = this.link,
            dueDate = this.validDate() ?: Date(),
            submissions = setOf(),
            course = courseId
        )
    }
    companion object {
        val INITIAL = UiState()
    }
}
