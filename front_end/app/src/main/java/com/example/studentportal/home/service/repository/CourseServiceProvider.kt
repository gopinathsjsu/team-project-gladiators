package com.example.studentportal.home.service.repository

import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.home.service.CourseService
import retrofit2.Retrofit

class CourseServiceProvider : ServiceProvider<CourseService> {
    override val retrofit: Retrofit get() = koin.get()

    override fun service(): CourseService {
        return retrofit.create(CourseService::class.java)
    }
}
