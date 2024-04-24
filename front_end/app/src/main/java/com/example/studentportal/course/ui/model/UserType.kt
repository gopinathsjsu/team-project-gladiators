package com.example.studentportal.course.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import com.example.studentportal.R

enum class UserType(
    val menuItems: List<CourseMenuItem>
) {
    FACULTY(
        menuItems = listOf(
            CourseMenuItem(
                titleRes = R.string.courses_menu_content,
                icon = Icons.Default.Info,
                command = Command.ShowContent
            ),
            CourseMenuItem(
                titleRes = R.string.courses_menu_students,
                icon = Icons.Default.Face,
                command = Command.ShowStudents
            ),
            CourseMenuItem(
                titleRes = R.string.courses_menu_assignments,
                icon = Icons.Default.Create,
                command = Command.ShowAssignments
            )
        )
    ),
    STUDENT(
        menuItems = listOf(
            CourseMenuItem(
                titleRes = R.string.courses_menu_content,
                icon = Icons.Default.Info,
                command = Command.ShowContent
            ),
            CourseMenuItem(
                titleRes = R.string.courses_menu_assignments,
                icon = Icons.Default.Create,
                command = Command.ShowAssignments
            )
        )
    ),
    ADMIN(
        menuItems = listOf(
            CourseMenuItem(
                titleRes = R.string.courses_menu_students,
                icon = Icons.Default.Face,
                command = Command.ShowStudents
            )
        )
    ),
    UNKNOWN(listOf());

    companion object {
        fun valueOf(name: String): UserType {
            return UserType.entries.firstOrNull { it.name == name } ?: UNKNOWN
        }
    }
}
