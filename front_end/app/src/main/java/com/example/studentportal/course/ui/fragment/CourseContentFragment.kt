package com.example.studentportal.course.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.course.ui.layout.CourseContentLayout
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseContentViewModel
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel
import com.example.studentportal.databinding.FragmentCourseBinding

class CourseContentFragment(
    viewModelFactory: ViewModelProvider.Factory = CourseContentViewModel.SyllabusViewModelFactory
) : BaseFragment<FragmentCourseBinding>(TAG) {
    internal val viewModel by viewModels<CourseContentViewModel> {
        viewModelFactory
    }

    val courseId: String
        get() = arguments?.getString(KEY_COURSE_ID).orEmpty()

    val userType: UserType
        get() = UserType.valueOf(requireArguments().getString(KEY_USER_TYPE).orEmpty())

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            CourseContentLayout(
                courseId = courseId,
                userType = userType,
                viewModel = viewModel,
                onAddClicked = {
                    viewModel.uiResultLiveData.value?.data?.courseUiModel?.let { uiModel ->
                        childFragmentManager.showBaseDialogFragment(
                            UpdateCourseContentFragment.newInstance(uiModel.id, uiModel.description)
                        )
                    }
                }
            )
        }
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            UpdateCourseContentFragment.KEY_FRAGMENT_RESULT,
            this
        ) { key, bundle ->
            bundle.getString(UpdateCourseContentFragment.KEY_NEW_CONTENT)?.let { content ->
                viewModel.updateContent(content = content) {
                    Toast.makeText(
                        requireContext(),
                        it.error?.message.orEmpty(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun menuItem() = R.id.nav_courses

    companion object {
        const val TAG = "SYLLABUS"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        fun newInstance(courseId: String, userType: String): CourseContentFragment {
            val fragment = CourseContentFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId,
                KEY_USER_TYPE to userType
            )
            return fragment
        }
    }
}
