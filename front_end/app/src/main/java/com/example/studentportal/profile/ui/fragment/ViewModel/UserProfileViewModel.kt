package com.example.studentportal.profile.ui.fragment.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentportal.profile.ui.fragment.Model.UserProfileModel
import org.jetbrains.annotations.VisibleForTesting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel(private val userApi: UserAPIService) : ViewModel() {
    @VisibleForTesting
    val _userProfileModel = MutableLiveData<UserProfileModel>()
    val userProfileModel: LiveData<UserProfileModel> = _userProfileModel

    init {
        fetchUserData()
    }

    fun fetchUserData() {
        userApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val student = response.body()?.find { it.type == "STUDENT" }
                if (student != null) {
                    _userProfileModel.postValue(
                        UserProfileModel(
                            userName = "${student.firstName} ${student.lastName}",
                            userQualification = "MS. Software Engineering", // Default or fetch as needed
                            userEmail = "${student.email}", // Default or fetch as needed
                            userPhone = "${student.phone}", // Default or fetch as needed
                            userBiography = "${student.biography}",
                            userLinks = emptyList()
                        )
                    )
                } else {
                    _userProfileModel.postValue(
                        UserProfileModel(
                            errorMessage = "No student found"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _userProfileModel.postValue(UserProfileModel(errorMessage = "Failed to load user data: ${t.message}"))
            }
        })
    }
}
