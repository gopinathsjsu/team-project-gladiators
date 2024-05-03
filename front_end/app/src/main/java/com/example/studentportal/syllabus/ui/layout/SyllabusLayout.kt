package com.example.studentportal.syllabus.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.syllabus.ui.model.SyllabusUiModel
import com.example.studentportal.syllabus.ui.viewModel.SyllabusViewModel

@Composable
fun SyllabusLayout(
    courseId: String,
    userType: UserType,
    viewModel: SyllabusViewModel,
    onAddClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchSyllabus(courseId = courseId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
            is BaseUiState.Success -> {
                ContentLayout(
                    syllabus = (uiState as BaseUiState.Success<SyllabusUiModel, DefaultError>).data
                        ?: SyllabusUiModel.empty()
                )
            }
            else -> Text(text = "Loading...")
        }
        if (userType == UserType.FACULTY) {
            FloatingActionButton(
                onClick = onAddClicked,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Edit Syllabus"
                )
            }
        }
    }
}

@Composable
fun ContentLayout(
    syllabus: SyllabusUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = syllabus.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally) // Aligns the course name in the center at the top
        )
        Column( // This Column will align the remaining details left under the course name
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Description: ${syllabus.description}",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
