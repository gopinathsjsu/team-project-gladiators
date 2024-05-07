package com.example.studentportal.course.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.course.ui.layout.UpdateCourseContentLayout
import com.example.studentportal.course.ui.viewmodel.UpdateCourseContentViewModel
import com.example.studentportal.databinding.FragmentCourseBinding

class UpdateCourseContentFragment : BaseDialogFragment<FragmentCourseBinding>(TAG) {
    internal val viewModel by viewModels<UpdateCourseContentViewModel> {
        UpdateCourseContentViewModel.UpdateCourseContentViewModelFactory
    }

    val courseId: String
        get() = requireArguments().getString(KEY_COURSE_ID).orEmpty()

    val initialContent: String
        get() = requireArguments().getString(KEY_CONTENT).orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateCourseContent(initialContent)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            UpdateCourseContentLayout(
                viewModel = viewModel,
                onCloseClicked = { dismiss() },
                onSubmitClicked = { content ->
                    setFragmentResult(
                        KEY_FRAGMENT_RESULT,
                        bundleOf(
                            KEY_NEW_CONTENT to content
                        )
                    )
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
        const val KEY_NEW_CONTENT = "KEY_NEW_SYLLABUS"
        const val KEY_CONTENT = "KEY_CONTENT"
        fun newInstance(courseId: String, content: String): UpdateCourseContentFragment {
            val fragment = UpdateCourseContentFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId,
                KEY_CONTENT to content
            )
            return fragment
        }
    }
}
