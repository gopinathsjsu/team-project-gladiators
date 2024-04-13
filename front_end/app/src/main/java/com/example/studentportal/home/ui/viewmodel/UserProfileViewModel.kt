package com.example.studentportal.home.ui.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {
    private val _userName = MutableLiveData("John Doe")
    val userName: LiveData<String> = _userName

    private val _userQualification = MutableLiveData("MS. Software Engineering")
    val userQualification: LiveData<String> = _userQualification

    private val _userEmail = MutableLiveData("john.doe@exampleuni.com")
    val userEmail: LiveData<String> = _userEmail

    private val _userPhone = MutableLiveData("(XXX) XXX XXXX")
    val userPhone: LiveData<String> = _userPhone

    private val _userBiography = MutableLiveData("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    val userBiography: LiveData<String> = _userBiography

    private val _userLinks = MutableLiveData<List<String>>()
    val userLinks: LiveData<List<String>> = _userLinks

    // The below functions are for updating the above fields
    fun updateUserEmail(newEmail: String) {
        _userEmail.value = newEmail
        // call a repository function to update the email in the backend
    }

    fun updateUserPhone(newPhone: String) {
        _userPhone.value = newPhone
    }

    fun updateUserBiography(newBiography: String) {
        _userBiography.value = newBiography
    }

    fun updateUserLinks(newLinks: List<String>) {
        _userLinks.value = newLinks
    }
}