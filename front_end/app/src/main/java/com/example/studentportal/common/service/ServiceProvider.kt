package com.example.studentportal.common.service

import retrofit2.Retrofit

interface ServiceProvider<Service> {
    val retrofit: Retrofit
    abstract fun service(): Service
}