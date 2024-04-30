package com.example.studentportal.profile.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.profile.service.UserService
import retrofit2.Retrofit

class UserServiceProvider : ServiceProvider<UserService> {
    override val retrofit: Retrofit
        get() = koin.get()

    override fun service(): UserService {
        return retrofit.create(UserService::class.java)
    }
}
