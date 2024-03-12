package com.example.studentportal.common.service

interface Repository<Service> {
    val provider: ServiceProvider<Service>
}
