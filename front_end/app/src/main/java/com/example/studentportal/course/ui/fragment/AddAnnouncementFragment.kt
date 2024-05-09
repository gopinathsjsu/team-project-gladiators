package com.example.studentportal.course.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.studentportal.assignment.ui.fragment.AddAssignmentFragment
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.course.ui.layout.AddAnnouncementLayout
import com.example.studentportal.course.ui.viewmodel.AddAnnouncementViewModel
import com.example.studentportal.databinding.FragmentAddAnnouncmentBinding
class AddAnnouncementFragment : BaseDialogFragment<FragmentAddAnnouncmentBinding>(TAG) {

    internal val viewModel by viewModels<AddAnnouncementViewModel> {
        AddAnnouncementViewModel.AddAnnouncementsViewModelFactory
    }

    val courseId: String
        get() = requireArguments().getString(AddAssignmentFragment.KEY_COURSE_ID).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddAnnouncmentBinding {
        val binding = FragmentAddAnnouncmentBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            AddAnnouncementLayout(
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
                            KEY_NEW_ANNOUNCEMENT to it
                        )
                    )
                    dismiss()
                }
            )
        }
        return binding
    }

    companion object {
        const val TAG = "ADD_ANNOUNCEMENT"
        const val KEY_COURSE_ID = "KET_COURSE_ID"
        const val KEY_FRAGMENT_RESULT = "KEY_FRAGMENT_RESULT"
        const val KEY_NEW_ANNOUNCEMENT = "KEY_NEW_ANNOUNCEMENT"
        fun newInstance(courseId: String): AddAnnouncementFragment {
            val fragment = AddAnnouncementFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId
            )
            return fragment
        }
    }
}
