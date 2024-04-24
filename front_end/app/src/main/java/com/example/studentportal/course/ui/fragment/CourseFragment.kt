package com.example.studentportal.course.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.course.ui.layout.CourseMenuLayout
import com.example.studentportal.course.ui.model.Command
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentCourseBinding

class CourseFragment : BaseFragment<FragmentCourseBinding>(TAG) {

    private val userType: UserType
        get() {
            val name = requireArguments().getString(KEY_USER_TYPE)
            return UserType.valueOf(name = name.orEmpty())
        }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        val binding = FragmentCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            CourseMenuLayout(userType = userType) {
                when (it) {
                    Command.ShowAssignments -> {
                        // TODO set up assignments
                    }
                    Command.ShowContent -> {
                        // TODO show course content
                    }
                    Command.ShowStudents -> {
                        // TODO show students
                    }
                    Command.Nothing -> Unit // Default
                }
            }
        }
        return binding
    }

    override fun menuItem(): Int = -1

    companion object {
        const val TAG = "COURSE"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"

        fun newInstance(
            userType: UserType,
            userId: String,
            courseId: String
        ): CourseFragment {
            val fragment = CourseFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId,
                KEY_USER_ID to userId,
                KEY_USER_TYPE to userType.name
            )
            return fragment
        }
    }
}
