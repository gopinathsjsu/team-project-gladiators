package com.example.studentportal.syllabus.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.studentportal.assignment.ui.fragment.AddAssignmentFragment
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.databinding.FragmentCourseBinding
import com.example.studentportal.syllabus.ui.layout.AddSyllabusLayout
import com.example.studentportal.syllabus.ui.viewModel.AddSyllabusViewModel

class AddSyllabusFragment : BaseDialogFragment<FragmentCourseBinding>(TAG) {
    internal val viewModel by viewModels<AddSyllabusViewModel> {
        AddSyllabusViewModel.AddSyllabusViewModelFactory
    }

    val courseId: String
        get() = requireArguments().getString(AddAssignmentFragment.KEY_COURSE_ID).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            AddSyllabusLayout(
                viewModel = viewModel,
                onCloseClicked = { dismiss() },
                onSubmitClicked = { syllabusText ->
                    viewModel.updateSyllabus(syllabusText)
                    dismiss()
                }
            )
        }
        return binding
    }

    companion object {
        const val TAG = "ADD_SYLLABUS"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"
        const val KEY_FRAGMENT_RESULT = "KEY_FRAGMENT_RESULT"
        const val KEY_NEW_SYLLABUS = "KEY_NEW_SYLLABUS"
        fun newInstance(courseId: String): AddSyllabusFragment {
            val fragment = AddSyllabusFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId
            )
            return fragment
        }
    }
}
