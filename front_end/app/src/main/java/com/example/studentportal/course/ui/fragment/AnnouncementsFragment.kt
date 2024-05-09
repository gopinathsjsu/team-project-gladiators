package com.example.studentportal.course.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.course.ui.layout.AnnouncementsLayout
import com.example.studentportal.course.ui.model.AnnouncementUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel
import com.example.studentportal.databinding.FragmentCourseBinding

class AnnouncementsFragment(
    viewModelFactory: ViewModelProvider.Factory = CourseDetailsViewModel.CourseViewModelFactory
) : BaseFragment<FragmentCourseBinding>(CourseFragment.TAG) {

    internal val viewModel by viewModels<CourseDetailsViewModel> {
        viewModelFactory
    }

    val userId: String
        get() = requireArguments().getString(KEY_USER_ID).orEmpty()

    val userType: UserType
        get() = UserType.valueOf(requireArguments().getString(KEY_USER_TYPE).orEmpty())

    val courseId: String
        get() = requireArguments().getString(KEY_COURSE).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            AnnouncementsLayout(
                viewModel = viewModel,
                courseId = courseId,
                userType = userType,
                onAddClicked = {
                    childFragmentManager.showBaseDialogFragment(
                        AddAnnouncementFragment.newInstance(courseId)
                    )
                }
            )
        }
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(AddAnnouncementFragment.KEY_FRAGMENT_RESULT, this) { key, bundle ->
            bundle.getParcelable<AnnouncementUiModel>(AddAnnouncementFragment.KEY_NEW_ANNOUNCEMENT)?.let {
                viewModel.createAnnouncement(it) {
                    Toast.makeText(
                        requireContext(),
                        it.error?.message.orEmpty(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun menuItem(): Int = -1

    companion object {
        const val TAG = "ASSIGNMENT"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_COURSE = "KEY_COURSE"

        fun newInstance(
            userType: UserType,
            userId: String,
            course: String
        ): AnnouncementsFragment {
            val fragment = AnnouncementsFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE to course,
                KEY_USER_ID to userId,
                KEY_USER_TYPE to userType.name
            )
            return fragment
        }
    }
}
