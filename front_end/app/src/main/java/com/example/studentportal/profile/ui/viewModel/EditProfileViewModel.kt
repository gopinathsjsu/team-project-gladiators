package com.example.studentportal.profile.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studentportal.common.ui.viewmodel.BaseViewModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.ui.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.annotations.VisibleForTesting

class EditProfileViewModel(
    dispatcher: CoroutineDispatcher,
    val user: UserUiModel?
) : BaseViewModel(dispatcher) {

    @VisibleForTesting
    internal val _uiResultLiveData = MutableLiveData(user?.toUiState() ?: UiState.INITIAL)
    val uiResultLiveData: LiveData<UiState>
        get() = _uiResultLiveData

    fun updateBiography(biography: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(biography = biography)
    }

    fun updateEmail(email: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(email = email)
    }

    fun updatePhone(phone: String) {
        val state = _uiResultLiveData.value ?: UiState.INITIAL
        _uiResultLiveData.value = state.copy(phone = phone)
    }

    class ViewModelProviderFactory(
        private val dispatcher: CoroutineDispatcher,
        private val userUiModel: UserUiModel?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return EditProfileViewModel(dispatcher, userUiModel) as T
        }
    }

    private fun UserUiModel.toUiState(): UiState {
        return UiState.INITIAL.copy(
            phone = phone,
            email = email,
            biography = biography
        )
    }

    data class UiState(
        val biography: String = "",
        val email: String = "",
        val phone: String = ""
    ) {
        fun readyToSubmit(): Boolean {
            return biography.isNotBlank() &&
                email.isNotBlank() &&
                phone.isNotBlank()
        }

        fun toUiModel(
            id: String,
            password: String,
            firstName: String,
            lastName: String,
            userType: UserType
        ): UserUiModel {
            return UserUiModel(
                id = id,
                password = password,
                biography = biography,
                email = email,
                phone = phone,
                firstName = firstName,
                lastName = lastName,
                type = userType
            )
        }
        companion object {
            val INITIAL = UiState()
        }
    }
}
