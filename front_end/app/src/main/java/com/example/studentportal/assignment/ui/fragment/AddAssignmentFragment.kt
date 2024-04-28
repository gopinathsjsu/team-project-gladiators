package com.example.studentportal.assignment.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.studentportal.assignment.ui.layout.AddAssignmentLayout
import com.example.studentportal.assignment.ui.viewmodel.AddAssignmentViewModel
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.databinding.FragmentAddAssignmentBinding

class AddAssignmentFragment : BaseDialogFragment<FragmentAddAssignmentBinding>(TAG) {
    internal val viewModel by viewModels<AddAssignmentViewModel> {
        AddAssignmentViewModel.AddAssignmentsViewModelFactory
    }

    val courseId: String
        get() = requireArguments().getString(KEY_COURSE_ID).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddAssignmentBinding {
        val binding = FragmentAddAssignmentBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            AddAssignmentLayout(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onCloseClicked = {
                    dialog?.dismiss()
                },
                courseId = courseId,
                onSubmitClicked = {
                    setFragmentResult(
                        KEY_FRAGMENT_RESULT,
                        bundleOf(
                            KEY_NEW_ASSIGNMENT to it
                        )
                    )
                    dismiss()
                }
            )
        }
        return binding
    }

    companion object {
        const val TAG = "ADD_ASSIGNMENT"
        const val KEY_COURSE_ID = "KET_COURSE_ID"
        const val KEY_FRAGMENT_RESULT = "KEY_FRAGMENT_RESULT"
        const val KEY_NEW_ASSIGNMENT = "KEY_NEW_ASSIGNMENT"
        fun newInstance(courseId: String): AddAssignmentFragment {
            val fragment = AddAssignmentFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId
            )
            return fragment
        }
    }
}
