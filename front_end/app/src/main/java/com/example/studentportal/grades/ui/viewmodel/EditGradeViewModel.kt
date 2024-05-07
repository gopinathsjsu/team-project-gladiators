package com.example.studentportal.grades.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.usecase.EditGradeUseCase
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

class EditGradeViewModel(
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {
    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(UiState.INITIAL)
    val uiResultLiveData: LiveData<UiState>
        get() = _uiResultLiveData

    fun updateScore(score: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(score = score)
    }
    fun updateSubmissionLink(submissionLink: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(submissionLink = submissionLink)
    }
    fun updateText(inputText: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(text = inputText)
    }

    fun onButtonClick(
        initialGrade: GradeUiModel,
        userType: UserType
    ) {
        val updatedGrade = _uiResultLiveData.value?.toUiModel(initialGrade, userType)
        if (updatedGrade != null) {
            updateObject(updatedGrade)
        }
    }

    fun updateObject(updatedGrade: GradeUiModel) {
        val newGrade = GradeUseCaseModel(
            id = updatedGrade.id,
            score = updatedGrade.score,
            studentId = updatedGrade.studentId,
            studentFirstName = updatedGrade.studentFirstName,
            studentLastName = updatedGrade.studentLastName,
            submissionLink = updatedGrade.submissionLink,
            assignmentId = null
        )
        viewModelScope.launch(dispatcher) {
            EditGradeUseCase(
                grade = newGrade,
                repository = koin.get()
            )
                .launch()
                .collectLatest { result ->
                    when (result) {
                        is UseCaseResult.Failure -> {
                            viewModelScope.launch {
                            }
                        }

                        is UseCaseResult.Success -> {
                            viewModelScope.launch {
                                updateScore(newGrade.score.toString())
                                updateSubmissionLink(newGrade.submissionLink.toString())
                                updateText("")
                            }
                        }
                    }
                }
        }
    }

    companion object {
        val EditGradeViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                EditGradeViewModel(
                    Dispatchers.IO
                )
            }
        }
    }
}
data class UiState(
    val text: String = "",
    val score: String = "-",
    val submissionLink: String = ""
) {
    fun toUiModel(
        initialGrade: GradeUiModel,
        userType: UserType
    ): GradeUiModel {
        return when (userType) {
            UserType.STUDENT -> GradeUiModel(
                id = initialGrade.id,
                score = initialGrade.score,
                studentFirstName = initialGrade.studentFirstName,
                studentLastName = initialGrade.studentLastName,
                studentId = initialGrade.studentId,
                submissionLink = text
            )
            else -> GradeUiModel(
                id = initialGrade.id,
                score = text.toIntOrNull() ?: -1,
                studentFirstName = initialGrade.studentFirstName,
                studentLastName = initialGrade.studentLastName,
                studentId = initialGrade.studentId,
                submissionLink = initialGrade.submissionLink
            )
        }
    }
    companion object {
        val INITIAL = UiState()
    }
}
