package com.example.studentportal.auth.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.studentportal.auth.ui.layout.AuthLayout
import com.example.studentportal.auth.ui.viewModel.AuthViewModel
import com.example.studentportal.common.di.getJwtToken
import com.example.studentportal.common.di.getUserId
import com.example.studentportal.common.di.getUserType
import com.example.studentportal.common.di.koin
import com.example.studentportal.databinding.ActivityLoginBinding
import com.example.studentportal.home.ui.activity.HomeActivity
import org.jetbrains.annotations.VisibleForTesting

class LoginActivity : FragmentActivity() {

    internal val viewModel by viewModels<AuthViewModel> {
        AuthViewModel.AuthViewModelFactory
    }

    @VisibleForTesting
    internal lateinit var binding: ActivityLoginBinding

    val sharedPreferences: SharedPreferences
        get() = koin.get<SharedPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater).initUI()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferences.getJwtToken().isNotBlank()) {
            startActivity(
                HomeActivity.intent(
                    this@LoginActivity,
                    userId = sharedPreferences.getUserId(),
                    userType = sharedPreferences.getUserType()
                )
            )
            return
        }
    }

    private fun ActivityLoginBinding.initUI(): ActivityLoginBinding {
        composeView.setContent {
            AuthLayout(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onSubmitClicked = {
                    viewModel.authenticate { user ->
                        startActivity(
                            HomeActivity.intent(
                                this@LoginActivity,
                                userId = user.id,
                                userType = user.type.name
                            )
                        )
                    }
                }
            )
        }
        return this
    }
}
