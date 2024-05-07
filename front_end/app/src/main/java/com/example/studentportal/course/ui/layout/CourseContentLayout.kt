package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.course.ui.model.CourseDetailsUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseContentViewModel

@Composable
fun CourseContentLayout(
    courseId: String,
    userType: UserType,
    viewModel: CourseContentViewModel,
    onAddClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchSyllabus(courseId = courseId)
    }

    when (uiState) {
        is BaseUiState.Error ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = uiState.error()?.message.orEmpty())
            }

        is BaseUiState.Success -> {
            ContentLayout(
                contentDetails = uiState?.data,
                userType = userType,
                onAddClicked = onAddClicked,
                modifier = Modifier.fillMaxSize()
            )
        }

        else ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Loading..."
                )
            }
    }
}

@Composable
fun ContentLayout(
    contentDetails: CourseDetailsUiModel?,
    userType: UserType,
    onAddClicked: () -> Unit,
    modifier: Modifier
) {
    ConstraintLayout(modifier.fillMaxSize()) {
        val (btnAdd, content, header) = createRefs()
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontSize = 24.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.courses_menu_content)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .constrainAs(content) {
                    top.linkTo(header.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            text = contentDetails?.courseUiModel?.description.orEmpty(),
            fontSize = 16.sp
        )
        if (userType == UserType.FACULTY) {
            FloatingActionButton(
                onClick = onAddClicked,
                modifier = Modifier
                    .testTag("editContent")
                    .padding(16.dp)
                    .constrainAs(btnAdd) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Syllabus"
                )
            }
        }
    }
}
