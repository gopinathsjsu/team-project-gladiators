package com.example.studentportal.profile.ui.fragment.Model

data class UserProfileModel(
    val userName: String = "John Doe",
    val userQualification: String = "MS. Software Engineering",
    val userEmail: String = "john.doe@exampleuni.com",
    val userPhone: String = "(XXX) XXX XXXX",
    val userBiography: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val userLinks: List<String> = emptyList(),
    val errorMessage: String? = null // Optional field for error messages
)
