package com.example.studentportal.home.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.databinding.FragmentCoursesBinding
import com.example.studentportal.home.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentCoursesBinding>(TAG) {
    internal val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.StudentViewModelFactory
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoursesBinding {
        val binding = FragmentCoursesBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            CoursesLayout(viewModel = viewModel)
        }
        return binding
    }

    override fun menuItem() = R.id.nav_courses

    companion object {
        const val TAG = "HOME"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}

@Composable
fun CoursesLayout(viewModel: HomeViewModel) {
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
