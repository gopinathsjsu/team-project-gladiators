package com.example.studentportal.common.di

import android.content.Context
import android.content.SharedPreferences
import com.example.studentportal.auth.usecase.model.AuthResponseUseCaseModel
import org.koin.dsl.module

const val APP_SHARED_PREFERENCES_KEY = "APP_SHARED_PREFERENCES"
const val APP_JWT_TOKEN_KEY = "APP_JWT_TOKEN_KEY"
const val APP_USER_ID = "APP_USER_ID"
const val APP_USER_TYPE = "APP_USER_TYPE"
const val APP_DISABLE_ANNOUNCEMENTS = "APP_DISABLE_ANNOUNCEMENTS"

val sharedPreferencesModule = module {
    single { get<Context>().getSharedPreferences(APP_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE) }
}

fun SharedPreferences.saveAuthenticatedUserData(authUsaCaseModel: AuthResponseUseCaseModel) {
    val preferences = this.edit()
    preferences.putString(APP_JWT_TOKEN_KEY, authUsaCaseModel.jwtToken)
    preferences.putString(APP_USER_ID, authUsaCaseModel.user.id)
    preferences.putString(APP_USER_TYPE, authUsaCaseModel.user.type)
    preferences.apply()
}

fun SharedPreferences.clearAuthenticatedUserData() {
    val preferences = this.edit()
    preferences.remove(APP_JWT_TOKEN_KEY)
    preferences.remove(APP_USER_ID)
    preferences.remove(APP_USER_TYPE)
    preferences.remove(APP_DISABLE_ANNOUNCEMENTS)
    preferences.apply()
}

fun SharedPreferences.getJwtToken(): String {
    return getString(APP_JWT_TOKEN_KEY, "").orEmpty()
}

fun SharedPreferences.getUserId(): String {
    return getString(APP_USER_ID, "").orEmpty()
}

fun SharedPreferences.getUserType(): String {
    return getString(APP_USER_TYPE, "").orEmpty()
}

fun SharedPreferences.hideAnnouncements(hide: Boolean) {
    val preferences = this.edit()
    preferences.putBoolean(APP_DISABLE_ANNOUNCEMENTS, hide)
    preferences.apply()
}

fun SharedPreferences.areAnnoucementsDisabled(): Boolean {
    return getBoolean(APP_DISABLE_ANNOUNCEMENTS, false)
}
