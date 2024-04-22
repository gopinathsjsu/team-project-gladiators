package com.example.studentportal.profile.ui.fragment.ViewModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {
    fun createUserApiService(): UserAPIService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Use 10.0.2.2 for the Android emulator to access localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPIService::class.java)
    }
}
