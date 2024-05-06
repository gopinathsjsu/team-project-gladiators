package com.example.studentportal.home.ui.layout

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.ui.viewmodel.HomeViewModel

@Composable
fun CoursesLayout(
    viewModel: HomeViewModel,
    args: Bundle,
    onClick: (course: BaseCourseUiModel.CourseUiModel) -> Unit,
    onAddClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState?.error?.message.orEmpty())
        is BaseUiState.Success -> {
            val courseList = uiState?.data?.uiModels
            if (!courseList.isNullOrEmpty()) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (courses, button) = createRefs()
                    CoursesList(
                        uiModels = courseList,
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(courses) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        onCourseClicked = onClick
                    )
                    if (args.getString(KEY_USER_TYPE) == UserType.ADMIN.name) {
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .testTag("addCourse")
                                .constrainAs(button) {
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                },
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            onClick = onAddClicked
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            } else {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (text, button) = createRefs()
                    Text(
                        modifier = Modifier.constrainAs(text) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = stringResource(id = R.string.courses_empty)
                    )
                    if (args.getString(KEY_USER_TYPE) == UserType.ADMIN.name) {
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .testTag("addCourse")
                                .constrainAs(button) {
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                },
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            onClick = onAddClicked
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        else -> Text(text = "Loading...")
    }
}

@Composable
fun CoursesList(
    uiModels: List<BaseCourseUiModel>,
    modifier: Modifier = Modifier,
    onCourseClicked: (course: BaseCourseUiModel.CourseUiModel) -> Unit
) {
    LazyColumn(modifier) {
        items(uiModels) { item ->
            when (item) {
                is BaseCourseUiModel.CourseUiModel -> CourseCard(
                    course = item,
                    onCourseClicked = onCourseClicked
                )

                is BaseCourseUiModel.FacultyUiModel -> FacultyHeaderCard(
                    professor = item
                )

                is BaseCourseUiModel.SemesterUiModel -> SemesterHeaderCard(
                    semester = item
                )
            }
        }
    }
}

@Composable
fun FacultyHeaderCard(professor: BaseCourseUiModel.FacultyUiModel, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = Modifier.background(Color.Black)
    ) {
        val (text, divider) = createRefs()
        Text(
            modifier = modifier
                .constrainAs(text) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .wrapContentSize(),
            text = "${professor.firstName} ${professor.lastName}",
            style = TextStyle(fontSize = 12.sp),
            color = Color.White
        )
        Divider(
            modifier = modifier
                .constrainAs(divider) {
                    top.linkTo(text.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
                .wrapContentSize(),
            color = Color.Black,
            thickness = 0.dp
        )
    }
}

@Composable
fun SemesterHeaderCard(semester: BaseCourseUiModel.SemesterUiModel, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = Modifier.background(Color.DarkGray)
    ) {
        val (text, divider) = createRefs()
        Text(
            modifier = modifier
                .constrainAs(text) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .wrapContentSize(),
            text = semester.name,
            color = Color.White,
            style = TextStyle(fontSize = 16.sp)
        )
        Divider(
            color = Color.DarkGray,
            thickness = 0.dp,
            modifier = modifier
                .constrainAs(divider) {
                    top.linkTo(text.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
                .wrapContentSize()
        )
    }
}

@Composable
fun CourseCard(
    course: BaseCourseUiModel.CourseUiModel,
    onCourseClicked: (course: BaseCourseUiModel.CourseUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier.clickable {
            onCourseClicked.invoke(course)
        }
    ) {
        val (text1, text2, divider, icon, warning) = createRefs()
        Text(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(text1) {
                    top.linkTo(parent.top, margin = 8.dp)
                },
            text = course.name,
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 22.sp)
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(text2) {
                    top.linkTo(text1.bottom)
                },
            text = course.description,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Divider(
            modifier = modifier
                .constrainAs(divider) {
                    top.linkTo(text2.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
                .wrapContentSize()
        )
        Icon(
            modifier = modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = null
        )
        if (!course.isPublished) {
            Text(
                modifier = modifier
                    .wrapContentHeight()
                    .constrainAs(warning) {
                        top.linkTo(text1.top)
                        end.linkTo(icon.start)
                    },
                text = stringResource(id = R.string.courses_unpublished),
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }
    }
}

val KEY_USER_ID = "KEY_USER_ID"
val KEY_USER_TYPE = "KEY_USER_TYPE"
