package com.example.studentportal.profile.ui.fragment.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.profile.ui.fragment.ViewModel.UserProfileViewModel
import com.example.studentportal.profile.ui.fragment.ViewModel.UserAPIService

class UserProfileViewModelFactory(private val userApiService: UserAPIService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(userApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
