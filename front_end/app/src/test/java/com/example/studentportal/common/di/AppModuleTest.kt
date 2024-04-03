package com.example.studentportal.common.di

import com.example.studentportal.common.service.KEY_BASE_URL
import com.example.studentportal.home.service.StudentService
import com.example.studentportal.home.service.repository.StudentRepository
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTestRule
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppModuleTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    @Test
    fun `test httpClient is present`() {
        val retrofitClient: Retrofit = koinTestRule.koin.get()
        assertThat(
            retrofitClient.baseUrl().toString()
        ).isEqualTo("$KEY_BASE_URL/") // Test Base Url
        assertThat(
            retrofitClient.callFactory()
        ).isInstanceOf(OkHttpClient::class.java) // Test Http Client
        assertThat(
            retrofitClient.converterFactories().firstOrNull {
                it is MoshiConverterFactory
            }
        ).isNotNull() // Test Json Converter
    }

    @Test
    fun `test student service`() {
        val retrofit: Retrofit = koinTestRule.koin.get()
        val studentRepo: StudentRepository = koinTestRule.koin.get()
        assertThat(studentRepo.provider.service()).isInstanceOf(StudentService::class.java)
        assertThat(studentRepo.provider.retrofit).isEqualTo(retrofit)
    }
}
