package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
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
import androidx.constraintlayout.compose.Dimension
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.course.ui.model.AnnouncementUiModel
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel

@Composable
fun AnnouncementsLayout(
    viewModel: CourseDetailsViewModel,
    courseId: String,
    userType: UserType,
    onAddClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCourseDetails(courseId = courseId)
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
            ConstraintLayout(
                modifier = Modifier
            ) {
                val (title, list, updateButton) = createRefs()
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                    fontSize = 24.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.announcement_title)
                )
                AnnouncementList(
                    modifier = Modifier.constrainAs(list) {
                        top.linkTo(title.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    announcements = uiState.data()?.announcements.orEmpty()
                )
                if (userType == UserType.FACULTY) {
                    FloatingActionButton(
                        onClick = onAddClicked,
                        modifier = Modifier
                            .testTag("addAnnouncement")
                            .padding(16.dp)
                            .constrainAs(updateButton) {
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            },
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
fun AnnouncementList(
    modifier: Modifier,
    announcements: List<AnnouncementUiModel>
) {
    if (announcements.isEmpty()) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.announcement_empty)
            )
        }
    } else {
        LazyColumn(modifier) {
            items(announcements) {
                ConstraintLayout(Modifier.fillMaxWidth()) {
                    val (title, description, divider) = createRefs()
                    Text(
                        text = it.title,
                        textAlign = TextAlign.Left,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .constrainAs(title) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    )
                    Text(
                        text = it.description,
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .constrainAs(description) {
                                top.linkTo(title.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    )
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp).constrainAs(divider) {
                            top.linkTo(description.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )
                }
            }
        }
    }
}
