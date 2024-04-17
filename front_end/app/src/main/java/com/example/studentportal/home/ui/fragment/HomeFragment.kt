package com.example.studentportal.home.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentCoursesBinding
import com.example.studentportal.home.ui.layout.CoursesLayout
import com.example.studentportal.home.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentCoursesBinding>(TAG) {
    internal val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.HomeViewModelFactory
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
