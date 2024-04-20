package com.example.studentportal.grades.service

import GradeService
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import retrofit2.Retrofit

class GradeServiceProvider : ServiceProvider<GradeService> {
    override val retrofit: Retrofit get() = koin.get()

    override fun service(): GradeService {
        return retrofit.create(GradeService::class.java)
    }
}