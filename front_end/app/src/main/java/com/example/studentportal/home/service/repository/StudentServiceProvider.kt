package com.example.studentportal.home.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.home.service.StudentService
import retrofit2.Retrofit

class StudentServiceProvider : ServiceProvider<StudentService> {
    override val retrofit: Retrofit get() = koin.get()

    override fun service(): StudentService {
        return retrofit.create(StudentService::class.java)
    }
}
