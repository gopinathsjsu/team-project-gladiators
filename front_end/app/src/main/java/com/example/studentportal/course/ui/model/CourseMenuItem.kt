package com.example.studentportal.course.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.FragmentManager
import com.example.studentportal.R
import com.example.studentportal.assignment.ui.fragment.AssignmentsFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.fragment.AnnouncementsFragment
import com.example.studentportal.course.ui.fragment.CourseContentFragment
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.students.ui.fragment.StudentsFragment

data class CourseMenuItem(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val command: Command
)

sealed interface Command {
    abstract fun showFragment(
        fragmentManager: FragmentManager,
        course: BaseCourseUiModel.CourseUiModel?,
        userId: String,
        userType: UserType
    )
    data object ShowStudents : Command {
        override fun showFragment(
            fragmentManager: FragmentManager,
            course: BaseCourseUiModel.CourseUiModel?,
            userId: String,
            userType: UserType
        ) {
            val fragment = StudentsFragment.newInstance(
                course?.id.orEmpty()
            )
            fragmentManager.showBaseFragment(
                fragment = fragment,
                addToBackStack = true,
                containerId = R.id.fl_content
            )
        }
    }
    data object ShowAssignments : Command {
        override fun showFragment(
            fragmentManager: FragmentManager,
            course: BaseCourseUiModel.CourseUiModel?,
            userId: String,
            userType: UserType
        ) {
            val fragment = AssignmentsFragment.newInstance(
                course?.id.orEmpty(),
                userId = userId,
                userType = userType.name
            )
            fragmentManager.showBaseFragment(
                fragment = fragment,
                addToBackStack = true,
                containerId = R.id.fl_content
            )
        }
    }

    data object ShowContent : Command {
        override fun showFragment(
            fragmentManager: FragmentManager,
            course: BaseCourseUiModel.CourseUiModel?,
            userId: String,
            userType: UserType
        ) {
            val fragment = CourseContentFragment.newInstance(
                course?.id.orEmpty(),
                userType.name
            )
            fragmentManager.showBaseFragment(
                fragment = fragment,
                addToBackStack = true,
                containerId = R.id.fl_content
            )
        }
    }

    data object Announcements : Command {
        override fun showFragment(
            fragmentManager: FragmentManager,
            course: BaseCourseUiModel.CourseUiModel?,
            userId: String,
            userType: UserType
        ) {
            val fragment = AnnouncementsFragment.newInstance(
                userType,
                userId,
                course?.id.orEmpty()
            )
            fragmentManager.showBaseFragment(
                fragment = fragment,
                addToBackStack = true,
                containerId = R.id.fl_content
            )
        }
    }

    data object Nothing : Command {
        override fun showFragment(
            fragmentManager: FragmentManager,
            course: BaseCourseUiModel.CourseUiModel?,
            userId: String,
            userType: UserType
        ) = Unit
    }
}
