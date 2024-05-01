package com.example.studentportal.auth.usecase.model

import com.example.studentportal.common.service.buildMoshi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import okhttp3.RequestBody
import okio.Buffer
import java.util.Base64

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "Username")
    val email: String,
    @Json(name = "Password")
    val password: String
) {
    fun encodeCredentials(): String {
        val credentials = "$email:$password"
        return Base64.getEncoder().encodeToString(credentials.toByteArray())
    }
}

fun RequestBody.authRequest(): AuthRequest? {
    val buffer = Buffer()
    writeTo(buffer)
    val jsonString = buffer.readUtf8()
    val adapter: JsonAdapter<AuthRequest> = buildMoshi().adapter(AuthRequest::class.java)
    return adapter.fromJson(jsonString)
}
