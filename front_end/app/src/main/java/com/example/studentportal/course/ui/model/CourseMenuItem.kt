package com.example.studentportal.course.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class CourseMenuItem(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val command: Command
)

sealed interface Command {
    object ShowStudents : Command
    object ShowAssignments : Command
    object ShowContent : Command
    object Nothing : Command
}
