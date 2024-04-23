package com.example.studentportal.profile.ui.fragment.ViewModel

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceFactory {
    fun createUserApiService(): UserAPIService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(NullToEmptyStringAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Use 10.0.2.2 for the Android emulator to access localhost
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Use Moshi as the JSON converter
            .build()
            .create(UserAPIService::class.java)
    }
}
