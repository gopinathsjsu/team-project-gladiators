package com.example.studentportal.grades.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentportal.grades.service.GradeRepository
import com.example.studentportal.grades.ui.model.GradeUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class EditGradeViewModel(
    val initialGrade: GradeUiModel,
    val repository: GradeRepository,
    val userType: String
) : ViewModel() {

    private val _grade = MutableStateFlow(initialGrade)
    val grade = _grade.asStateFlow()

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text

    var showDialog by mutableStateOf(false)
    var dialogMessage by mutableStateOf("")

    fun updateText(newText: String) {
        _text.value = newText
    }

    fun onButtonClick() {
        val updatedGrade = when (userType) {
            "STUDENT" -> GradeUiModel(
                id = initialGrade.id,
                score = initialGrade.score,
                studentFirstName = initialGrade.studentFirstName,
                studentLastName = initialGrade.studentLastName,
                studentId = initialGrade.studentId,
                submissionLink = text.value
            )
            else -> GradeUiModel(
                id = initialGrade.id,
                score = text.value.toIntOrNull() ?: -1,
                studentFirstName = initialGrade.studentFirstName,
                studentLastName = initialGrade.studentLastName,
                studentId = initialGrade.studentId,
                submissionLink = initialGrade.submissionLink
            )
        }
        updateObject(updatedGrade)
    }

    fun updateObject(updatedGrade: GradeUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateGrade(updatedGrade)
            if (response.isSuccessful) {
                _grade.value = updatedGrade
                _text.value = ""
            } else {
                val message = response.errorBody()?.string() ?: "Unknown error"
                dialogMessage = "Failed to update grade: $message"
                showDialog = true
            }
        }
    }

    fun dismissDialog() {
        showDialog = false
    }

    companion object {
        fun koinModule(): Module {
            return module {
                viewModel { (grade: GradeUiModel, userType: String) ->
                    EditGradeViewModel(
                        initialGrade = grade,
                        repository = get(),
                        userType = userType
                    )
                }
            }
        }
    }
}
