package com.example.studentportal.profile.ui.fragment.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentportal.profile.ui.fragment.Model.UserProfileModel
import org.jetbrains.annotations.VisibleForTesting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileViewModel : ViewModel() {
    @VisibleForTesting
    val _userProfileModel = MutableLiveData<UserProfileModel>()
    val userProfileModel: LiveData<UserProfileModel> = _userProfileModel

    private val userApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")  // Use 10.0.2.2 for the Android emulator to access localhost
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserAPIService::class.java)

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        userApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val student = response.body()?.find { it.type == "STUDENT" }
                if (student != null) {
                    _userProfileModel.postValue(UserProfileModel(
                        userName = "${student.firstName} ${student.lastName}",
                        userQualification = "MS. Software Engineering", // Default or fetch as needed
                        userEmail = "john.doe@exampleuni.com",  // Default or fetch as needed
                        userPhone = "(XXX) XXX XXXX",  // Default or fetch as needed
                        userBiography = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        userLinks = emptyList()
                    ))
                } else {
                    _userProfileModel.postValue(UserProfileModel(
                        errorMessage = "No student found"
                    ))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _userProfileModel.postValue(UserProfileModel(errorMessage = "Failed to load user data: ${t.message}"))
            }
        })
    }
//    fun updateUserProfile(update: UserProfileModel.() -> UserProfileModel) {
//
//        _userProfileModel.value = _userProfileModel.value?.update()
//    }
}