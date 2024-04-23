package com.example.studentportal.profile.ui.fragment.ViewModel

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(biography: String?): String {
        return biography ?: "No biography available"
    }

    @ToJson
    fun toJson(biography: String): String {
        return biography
    }
}
