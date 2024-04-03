package com.example.studentportal.home.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.home.ui.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {

    val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.StudentViewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserDetailsLayout(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun UserDetailsLayout(viewModel: HomeViewModel) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchStudent("9")
    }

    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
        is BaseUiState.Success -> Text(text = "Hello ${uiState.data()?.name} your email is ${uiState.data()?.email}")
        else -> Text(text = "Loading...")
    }
}
