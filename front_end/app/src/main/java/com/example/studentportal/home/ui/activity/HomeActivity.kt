package com.example.studentportal.home.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.studentportal.common.ui.model.BaseUiResult
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.ui.theme.MyApplicationTheme
import com.example.studentportal.home.ui.viewmodel.UserViewModel
import java.lang.reflect.Modifier

class HomeActivity : ComponentActivity() {

    val viewModel by viewModels<UserViewModel> {
        UserViewModel.StudentViewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                UserDetails(
                    viewModel = viewModel,
                    modifier = Modifier()
                )
            }
        }
    }
}

@Composable
fun UserDetails(viewModel: UserViewModel, modifier: Modifier = Modifier()) {
    val state by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchStudent("9")
    }

    when(state){
        is BaseUiResult.Error ->  Text(text = "ERROR ${state?.error()?.message}")
        is BaseUiResult.Loading -> Text(text = "Loading...")
        is BaseUiResult.Success ->  Text(text = "Hello ${state?.data()?.name ?: "NOT FOUND"} your email is  ${state?.data()?.email ?: "NOT FOUND"}")
        null -> Text(text = "Hello NOT FOUND")
    }
}
