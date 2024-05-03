package com.example.studentportal.common.di

import com.example.studentportal.assignment.service.repository.AssignmentRepository
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.home.service.repository.CourseRepository
import com.example.studentportal.notifications.service.repository.NotificationRepository
import com.example.studentportal.profile.service.repository.UserRepository
import com.example.studentportal.students.service.repository.StudentRepository
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

val appModule = module {
    includes(serviceModule)
    includes(sharedPreferencesModule)
    includes(NotificationRepository.koinModule())
    includes(StudentRepository.koinModule())
    includes(CourseRepository.koinModule())
    includes(UserRepository.koinModule())
    includes(AssignmentRepository.koinModule())
}

val koin: Koin
    get() = GlobalContext.get()
