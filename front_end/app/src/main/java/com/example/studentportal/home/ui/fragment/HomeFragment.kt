package com.example.studentportal.home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.course.ui.activtiy.CourseActivity
import com.example.studentportal.course.ui.fragment.CourseInputFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentCoursesBinding
import com.example.studentportal.home.ui.layout.CoursesLayout
import com.example.studentportal.home.ui.layout.KEY_USER_ID
import com.example.studentportal.home.ui.layout.KEY_USER_TYPE
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment(
    viewModelFactory: ViewModelProvider.Factory = HomeViewModel.HomeViewModelFactory
) : BaseFragment<FragmentCoursesBinding>(TAG) {
    internal val viewModel by viewModels<HomeViewModel> {
        viewModelFactory
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
                },
                onAddClicked = {
                    childFragmentManager.showBaseDialogFragment(
                        CourseInputFragment.newInstance()
                    )
                }
            )
        }
        return binding
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.fetchCourses(arguments?.getString(KEY_USER_ID))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(CourseInputFragment.KEY_FRAGMENT_RESULT, this) { key, bundle ->
            bundle.getParcelable<BaseCourseUiModel.CourseUiModel>(CourseInputFragment.KEY_COURSE_INPUT)?.let {
                viewModel.createNewCourse(
                    it,
                    onError = {
                        Toast.makeText(
                            requireContext(),
                            it.error?.message.orEmpty(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }

    override fun menuItem() = R.id.nav_courses

    companion object {
        const val TAG = "HOME"
        fun newInstance(userId: String, userType: UserType): HomeFragment {
            val fragments = HomeFragment()
            fragments.arguments = bundleOf(
                KEY_USER_ID to userId,
                KEY_USER_TYPE to userType.name
            )
            return fragments
        }
    }
}
