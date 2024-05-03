package com.example.studentportal.syllabus.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentCourseBinding
import com.example.studentportal.syllabus.ui.layout.SyllabusLayout
import com.example.studentportal.syllabus.ui.viewModel.SyllabusViewModel

class SyllabusFragment : BaseFragment<FragmentCourseBinding>(TAG) {
    internal val viewModel by viewModels<SyllabusViewModel> {
        SyllabusViewModel.SyllabusViewModelFactory
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
            SyllabusLayout(
                courseId = courseId,
                userType = userType,
                viewModel = viewModel,
                onAddClicked = {
                    childFragmentManager.showBaseDialogFragment(
                        AddSyllabusFragment.newInstance(courseId)
                    )
                }
            )
        }
        return binding
    }

    override fun menuItem() = R.id.nav_courses

    companion object {
        const val TAG = "SYLLABUS"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        fun newInstance(courseId: String, userType: String): SyllabusFragment {
            val fragment = SyllabusFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId,
                KEY_USER_TYPE to userType
            )
            return fragment
        }
    }
}
