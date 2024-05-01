package com.example.studentportal.common.service

import com.example.studentportal.auth.AuthInterceptor
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val serviceModule = module {
    single {
        buildRetrofitClient()
    }
}

private fun buildRetrofitClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(KEY_BASE_URL)
        .client(buildOkHttpClient())
        .addConverterFactory(
            MoshiConverterFactory.create(
                buildMoshi()
            )
        )
        .build()
}

fun buildMoshi(): Moshi {
    return Moshi.Builder()
        .add(DateJsonAdapter())
        .add(BaseCourseUseCaseModel.Type.moshiPolymorphicFactory())
        .add(KotlinJsonAdapterFactory())
        .build()
}
private fun buildOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AuthInterceptor())
        .build()
}

const val KEY_BASE_URL = "http://10.0.2.2:8080"
