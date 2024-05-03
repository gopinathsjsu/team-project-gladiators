package com.example.studentportal.syllabus.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.syllabus.service.SyllabusService
import retrofit2.Retrofit
import retrofit2.create

class SyllabusServiceProvider : ServiceProvider<SyllabusService> {
    override val retrofit: Retrofit
        get() = koin.get()

    override fun service(): SyllabusService {
        return retrofit.create(SyllabusService::class.java)
    }
}
