package com.example.studentportal.home.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.course.ui.activtiy.CourseActivity
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentCoursesBinding
import com.example.studentportal.home.ui.layout.CoursesLayout
import com.example.studentportal.home.ui.layout.KEY_USER_ID
import com.example.studentportal.home.ui.layout.KEY_USER_TYPE
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
            CoursesLayout(
                viewModel = viewModel,
                args = requireArguments(),
                onClick = { course ->
                    startActivity(
                        CourseActivity.intent(
                            requireActivity(),
                            course = course,
                            userId = requireArguments().getString(KEY_USER_ID).orEmpty(),
                            userType = requireArguments().getString(KEY_USER_TYPE) ?: UserType.UNKNOWN.name
                        )
                    )
                }
            )
        }
        return binding
    }

    override fun menuItem() = R.id.nav_courses

    companion object {
        const val TAG = "HOME"
        fun newInstance(): HomeFragment {
            val fragments = HomeFragment()
            fragments.arguments = bundleOf(
                KEY_USER_ID to "b0f52f07-86a7-4abe-b71a-9ef9212b303d",
                KEY_USER_TYPE to UserType.FACULTY.name
            )
            return fragments
        }
    }
}
