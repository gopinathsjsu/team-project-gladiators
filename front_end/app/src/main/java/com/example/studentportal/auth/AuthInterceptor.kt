package com.example.studentportal.auth

import android.content.SharedPreferences
import com.example.studentportal.auth.usecase.model.authRequest
import com.example.studentportal.common.di.getJwtToken
import com.example.studentportal.common.di.koin
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: () -> String = {
        koin.get<SharedPreferences>().getJwtToken()
    }
) : Interceptor {
    private val jwtToken: String
        get() = tokenProvider.invoke()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (chain.request().url.pathSegments.any { it.contains("token") }) {
            val authRequest = request.body?.authRequest()
            chain.proceed(
                request.newBuilder()
                    .addHeader("Authorization", "Basic ${authRequest?.encodeCredentials().orEmpty()}")
                    .build()
            )
        } else if (jwtToken.isNotBlank()) {
            chain.proceed(
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $jwtToken")
                    .build()
            )
        } else {
            chain.proceed(request)
        }
    }
}
