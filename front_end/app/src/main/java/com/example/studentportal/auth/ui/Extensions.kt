package com.example.studentportal.auth.ui

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.studentportal.R

fun showLogoutDialog(context: Context, onLoggedOut: () -> Unit) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.auth_logout_title))
    builder.setMessage(context.getString(R.string.auth_logout_message))
    builder.setPositiveButton(context.getString(R.string.auth_yes)) { _, _ ->
        onLoggedOut.invoke()
    }
    builder.setNegativeButton(context.getString(R.string.auth_no)) { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}
