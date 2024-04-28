package com.example.studentportal.assignment.service.repository

import com.example.studentportal.assignment.service.AssignmentService
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import retrofit2.Retrofit

class AssignmentServiceProvider : ServiceProvider<AssignmentService> {
    override val retrofit: Retrofit
        get() = koin.get()

    override fun service(): AssignmentService {
        return retrofit.create(AssignmentService::class.java)
    }
}
