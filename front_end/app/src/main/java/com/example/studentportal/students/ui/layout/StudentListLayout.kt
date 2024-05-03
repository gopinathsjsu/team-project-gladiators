package com.example.studentportal.students.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.students.ui.model.StudentListUiModel
import com.example.studentportal.students.ui.model.StudentType
import com.example.studentportal.students.ui.model.StudentUiModel
import com.example.studentportal.students.ui.viewmodel.StudentListViewModel

@Composable
fun StudentListLayout(
    courseId: String,
    viewModel: StudentListViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    // API call with course ID
    LaunchedEffect(courseId) {
        viewModel.fetchStudents(courseId)
    }

    val uiState by viewModel.uiResultLiveData.observeAsState()
    when (uiState) {
        is BaseUiState.Error -> Text(text = (uiState as BaseUiState.Error<StudentListUiModel, DefaultError>).error?.message.orEmpty())
        is BaseUiState.Success -> {
            val studentList = (uiState as BaseUiState.Success<StudentListUiModel, DefaultError>).data?.students
            if (!studentList.isNullOrEmpty()) {
                StudentList(
                    studentList = studentList,
                    modifier = modifier
                )
            } else {
                Text(stringResource(id = R.string.empty_students))
            }
        }
        else -> Text(text = "Loading...")
    }
}

@Composable
fun StudentList(studentList: List<StudentUiModel>, modifier: Modifier) {
    LazyColumn(modifier) {
        items(studentList) { student ->
            StudentCard(
                student = student,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun StudentCard(student: StudentUiModel, modifier: Modifier) {
    val textStyle = TextStyle(fontSize = 18.sp)
    val studentTypeText = when (student.type) {
        StudentType.UNDERGRADUATE -> stringResource(id = R.string.undergraduate)
        StudentType.GRADUATE -> stringResource(id = R.string.graduate)
        StudentType.PHD -> stringResource(id = R.string.phd)
        else -> stringResource(id = R.string.unknown_student_type)
    }

    Box(modifier = modifier.padding(4.dp)) {
        Column {
            Text(
                text = "${student.firstName} ${student.lastName}",
                style = textStyle
            )
            Text(
                text = "Email: ${student.email}",
                style = textStyle
            )
            if (student.phone != null) {
                Text(
                    text = "Phone: ${student.phone}",
                    style = textStyle
                )
            }
            Text(
                text = "Type: $studentTypeText",
                style = textStyle.copy(fontSize = 16.sp) // Smaller text for type
            )
            Divider()
        }
    }
}
