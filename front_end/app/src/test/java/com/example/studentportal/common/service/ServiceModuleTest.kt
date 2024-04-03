package com.example.studentportal.common.service

import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTestRule
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceModuleTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(serviceModule)
    }

    @Test
    fun `test buildRetrofitClient`() {
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
}
