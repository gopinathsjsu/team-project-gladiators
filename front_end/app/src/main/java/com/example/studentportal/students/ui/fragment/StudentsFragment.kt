package com.example.studentportal.students.ui.fragment

import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.fragment.CourseFragment
import com.example.studentportal.databinding.FragmentStudentsBinding
import com.example.studentportal.profile.ui.fragment.ProfileFragment
import com.example.studentportal.students.ui.layout.StudentListLayout
import com.example.studentportal.students.ui.viewmodel.StudentListViewModel

class StudentsFragment(
    viewModelFactory: ViewModelProvider.Factory = StudentListViewModel.StudentListViewModelFactory
) : BaseFragment<FragmentStudentsBinding>(TAG) {

    internal val viewModel by viewModels<StudentListViewModel> { viewModelFactory }

    val courseId: String
        get() = requireArguments().getString(KEY_COURSE_ID).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStudentsBinding {
        val binding = FragmentStudentsBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            StudentListLayout(
                modifier = Modifier.fillMaxSize(),
                courseId = courseId,
                viewModel = viewModel,
                onClick = {
                    val fragment = ProfileFragment.newInstance(
                        userId = it.id
                    )
                    parentFragmentManager.showBaseFragment(
                        fragment = fragment,
                        addToBackStack = true,
                        containerId = R.id.fl_content
                    )
                }
            )
        }
        return binding
    }

    override fun menuItem(): Int = -1

    companion object {
        const val TAG = "STUDENTS"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"

        fun newInstance(courseId: String): StudentsFragment {
            val fragment = StudentsFragment()
            fragment.arguments = bundleOf(KEY_COURSE_ID to courseId)
            return fragment
        }
    }
}
