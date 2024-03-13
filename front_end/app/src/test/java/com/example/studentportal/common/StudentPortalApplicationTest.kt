package com.example.studentportal.common

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.service.KEY_BASE_URL
import com.example.studentportal.home.service.StudentService
import com.example.studentportal.home.service.repository.StudentRepository
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(AndroidJUnit4::class)
class StudentPortalApplicationTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test application setUp`() {
        // Verify Retrofit SetUp
        val retrofit = koin.get<Retrofit>()
        assertThat(
            retrofit.baseUrl().toString()
        ).isEqualTo("$KEY_BASE_URL/") // Test Base Url
        assertThat(
            retrofit.callFactory()
        ).isInstanceOf(OkHttpClient::class.java) // Test Http Client
        assertThat(
            retrofit.converterFactories().firstOrNull {
                it is MoshiConverterFactory
            }
        ).isNotNull() // Test Json Converter

        // Verify Student Service
        val studentRepo: StudentRepository = koin.get()
        assertThat(studentRepo.provider.service()).isInstanceOf(StudentService::class.java)
        assertThat(studentRepo.provider.retrofit).isEqualTo(retrofit)
    }
}
