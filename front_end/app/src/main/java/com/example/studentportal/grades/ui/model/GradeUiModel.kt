package com.example.studentportal.grades.ui.model

import android.os.Parcelable
import com.example.studentportal.common.ui.model.BaseUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GradeUiModel(
    val id: String,
    val score: Int,
    val studentFirstName: String = "",
    val studentLastName: String = "",
    val studentId: String,
    val submissionLink: String?
) : BaseUiModel, Parcelable
