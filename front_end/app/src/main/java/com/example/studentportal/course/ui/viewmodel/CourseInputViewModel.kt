package com.example.studentportal.course.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.common.usecase.UseCaseResult
import com.example.studentportal.course.ui.model.SemesterUiModel
import com.example.studentportal.course.usecase.CourseInputDataUseCase
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.profile.ui.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import java.util.UUID

class CourseInputViewModel(
    dispatcher: CoroutineDispatcher,
    val existingCourse: BaseCourseUiModel.CourseUiModel?
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(existingCourse?.toUiState() ?: UiState.INITIAL)
    val uiResultLiveData: LiveData<UiState>
        get() = _uiResultLiveData

    suspend fun fetchInputData() {
        viewModelScope.launch(dispatcher) {
            CourseInputDataUseCase(
                repository = koin.get()
            ).launch().collectLatest { result ->
                if (result is UseCaseResult.Success) {
                    viewModelScope.launch {
                        val state = _uiResultLiveData.value ?: UiState.INITIAL
                        val uiModel = result.data?.toUiModel()
                        _uiResultLiveData.value = state.copy(
                            selectedUser = state.selectedUser ?: uiModel?.defaultSelectedInstructor(existingCourse?.instructor),
                            selectedSemester = state.selectedSemester ?: uiModel?.defaultSelectedSemester(existingCourse?.semester),
                            users = uiModel?.instructors.orEmpty(),
                            semesters = uiModel?.semesters.orEmpty()
                        )
                    }
                }
            }
        }
    }

    fun updateName(name: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(name = name)
    }

    fun updateDescription(description: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(description = description)
    }

    fun updateSelectedSemester(semesterUiModel: SemesterUiModel) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(selectedSemester = semesterUiModel)
    }

    fun updateSelectedInstructor(userUiModel: UserUiModel) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(selectedUser = userUiModel)
    }

    class ViewModelProviderFactory(
        private val dispatcher: CoroutineDispatcher,
        private val courseUiModel: BaseCourseUiModel.CourseUiModel?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return CourseInputViewModel(dispatcher, courseUiModel) as T
        }
    }

    private fun BaseCourseUiModel.CourseUiModel.toUiState(): UiState {
        return UiState.INITIAL.copy(
            name = this.name,
            description = this.description
        )
    }

    data class UiState(
        val name: String = "",
        val description: String = "",
        val selectedSemester: SemesterUiModel? = null,
        val semesters: List<SemesterUiModel> = listOf(),
        val users: List<UserUiModel> = listOf(),
        val selectedUser: UserUiModel? = null
    ) {
        fun readyToSubmit(): Boolean {
            return name.isNotBlank() &&
                selectedUser != null &&
                selectedSemester != null &&
                description.isNotBlank()
        }

        fun toUiModel(id: String?): BaseCourseUiModel.CourseUiModel {
            return BaseCourseUiModel.CourseUiModel(
                id = id ?: UUID.randomUUID().toString(),
                name = this.name,
                instructor = selectedUser?.id.orEmpty(),
                enrolledStudents = setOf(),
                assignments = setOf(),
                semester = selectedSemester?.id.orEmpty(),
                isPublished = false,
                description = description
            )
        }
        companion object {
            val INITIAL = UiState()
        }
    }
}
