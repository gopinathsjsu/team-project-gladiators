package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowRight
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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
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
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.course.ui.model.Command
import com.example.studentportal.course.ui.model.CourseMenuItem
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.course.ui.viewmodel.CourseDetailsViewModel
import java.lang.Error
import java.util.Locale

@Composable
fun CourseMenuLayout(
    userType: UserType,
    courseId: String,
    viewModel: CourseDetailsViewModel,
    modifier: Modifier = Modifier,
    onClicked: (command: Command) -> Unit,
    onEditClicked: (userType: UserType) -> Unit
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier,
                    text = uiState?.error?.message.orEmpty()
                )
            }

        is BaseUiState.Success -> ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (instructorFullName, label, menuItems, editButton, warning) = createRefs()
            Text(
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp)
                    .constrainAs(label) {
                        bottom.linkTo(instructorFullName.bottom)
                        top.linkTo(instructorFullName.top)
                        start.linkTo(parent.start)
                    },
                fontSize = 14.sp,
                text = stringResource(id = R.string.courses_instructor)
            )
            Text(
                modifier = Modifier
                    .padding(top = 32.dp, start = 8.dp, end = 16.dp)
                    .constrainAs(instructorFullName) {
                        top.linkTo(parent.top)
                        start.linkTo(label.end)
                    },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = uiState.data()?.instructorUiModel?.fullName
                    ?: stringResource(id = R.string.courses_not_assigned)
            )
            if (uiState.data()?.courseUiModel?.isPublished == false) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .constrainAs(warning) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                    text = stringResource(id = R.string.courses_unpublished).uppercase(Locale.getDefault()),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                )
            }
            LazyColumn(
                modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .constrainAs(menuItems) {
                        top.linkTo(instructorFullName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                items(userType.menuItems) {
                    MenuItem(
                        menuItem = it,
                        onClicked = onClicked
                    )
                }
            }
            if (userType == UserType.ADMIN) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag("editCourse")
                        .constrainAs(editButton) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    onClick = {
                        onEditClicked.invoke(userType)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null
                    )
                }
            } else if (userType == UserType.FACULTY && uiState?.data?.courseUiModel?.isPublished == false) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag("editCourse")
                        .constrainAs(editButton) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    onClick = {
                        onEditClicked.invoke(userType)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null
                    )
                }
            }
        }

        else -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Loading..."
            )
        }
    }
}

@Composable
fun MenuItem(
    menuItem: CourseMenuItem,
    onClicked: (command: Command) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .clickable {
                onClicked.invoke(menuItem.command)
            }
    ) {
        val (icon, title, arrow, divider) = createRefs()
        Icon(
            modifier = modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            imageVector = menuItem.icon,
            contentDescription = null
        )
        Text(
            modifier = modifier
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(title) {
                    top.linkTo(icon.top)
                    bottom.linkTo(icon.bottom)
                    start.linkTo(icon.end)
                },
            text = stringResource(id = menuItem.titleRes),
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 22.sp)
        )
        Icon(
            modifier = modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = null
        )
        Divider(
            modifier = modifier
                .constrainAs(divider) {
                    top.linkTo(parent.bottom, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
                .wrapContentSize()
        )
    }
}
