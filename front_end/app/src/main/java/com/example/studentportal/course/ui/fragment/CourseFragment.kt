package com.example.studentportal.course.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.R
import com.example.studentportal.assignment.ui.fragment.AssignmentsFragment
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.layout.CourseMenuLayout
import com.example.studentportal.course.ui.model.Command
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel
import com.example.studentportal.databinding.FragmentCourseBinding
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.students.ui.fragment.StudentsFragment

class CourseFragment(
    viewModelFactory: ViewModelProvider.Factory = CourseDetailsViewModel.CourseViewModelFactory
) : BaseFragment<FragmentCourseBinding>(TAG) {

    internal val viewModel by viewModels<CourseDetailsViewModel> {
        viewModelFactory
    }

    private val userType: UserType
        get() {
            val name = requireArguments().getString(KEY_USER_TYPE)
            return UserType.valueOf(name = name.orEmpty())
        }

    private val course: BaseCourseUiModel.CourseUiModel?
        get() {
            return requireArguments().getParcelable(KEY_COURSE)
        }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            CourseMenuLayout(
                modifier = Modifier.fillMaxSize(),
                userType = userType,
                viewModel = viewModel,
                courseId = course?.id.orEmpty(),
                onClicked = {
                    when (it) {
                        Command.ShowAssignments -> {
                            val fragment = AssignmentsFragment.newInstance(
                                course?.id.orEmpty(),
                                userType.name
                            )
                            parentFragmentManager.showBaseFragment(
                                fragment = fragment,
                                addToBackStack = true,
                                containerId = R.id.fl_content
                            )
                        }

                        Command.ShowContent -> {
                            // TODO show course content
                        }

                        Command.ShowStudents -> {
                            val fragment = StudentsFragment.newInstance(
                                course?.id.orEmpty()
                            )
                            parentFragmentManager.showBaseFragment(
                                fragment = fragment,
                                addToBackStack = true,
                                containerId = R.id.fl_content
                            )
                        }

                        Command.Nothing -> Unit // Default
                    }
                },
                onEditClicked = {
                    if (it == UserType.ADMIN) {
                        childFragmentManager.showBaseDialogFragment(
                            CourseInputFragment.newInstance(
                                course = course
                            )
                        )
                    } else if (it == UserType.FACULTY) {
                        showPublishDialog(requireContext()) {
                            viewModel.uiResultLiveData.value?.data?.courseUiModel?.let {
                                viewModel.updateCourse(
                                    it.copy(isPublished = true),
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
                }
            )
        }
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(CourseInputFragment.KEY_FRAGMENT_RESULT, this) { key, bundle ->
            bundle.getParcelable<BaseCourseUiModel.CourseUiModel>(CourseInputFragment.KEY_COURSE_INPUT)?.let {
                viewModel.updateCourse(
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

    override fun menuItem(): Int = -1

    companion object {
        const val TAG = "COURSE"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_COURSE = "KEY_COURSE"

        fun newInstance(
            userType: UserType,
            userId: String,
            course: BaseCourseUiModel.CourseUiModel?
        ): CourseFragment {
            val fragment = CourseFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE to course,
                KEY_USER_ID to userId,
                KEY_USER_TYPE to userType.name
            )
            return fragment
        }
    }
}

fun showPublishDialog(context: Context, onPublish: () -> Unit) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.courses_publish_title))
    builder.setMessage(context.getString(R.string.courses_publish_message))
    builder.setPositiveButton(context.getString(R.string.auth_yes)) { _, _ ->
        onPublish.invoke()
    }
    builder.setNegativeButton(context.getString(R.string.auth_no)) { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}
