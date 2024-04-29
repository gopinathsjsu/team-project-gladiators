package com.example.studentportal.assignment.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.assignment.ui.viewmodel.AssignmentsViewModel
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.course.ui.model.UserType

@Composable
fun AssignmentListLayout(
    courseId: String,
    userType: UserType,
    viewModel: AssignmentsViewModel,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // API call
    LaunchedEffect(Unit) {
        viewModel.fetchAssignments(courseId)
    }

    ConstraintLayout(modifier = modifier) {
        val (assignments, button) = createRefs()
        AssignmentList(
            viewModel = viewModel,
            modifier = Modifier.constrainAs(assignments) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
        if (userType == UserType.FACULTY) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("addAssignment")
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

@Composable
fun AssignmentList(
    viewModel: AssignmentsViewModel,
    modifier: Modifier
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
        is BaseUiState.Success -> {
            val assignments = uiState.data()?.uiModels
            if (!assignments.isNullOrEmpty()) {
                LazyColumn(modifier.fillMaxSize()) {
                    items(assignments) {
                        AssignmentItem(assignment = it)
                    }
                }
            } else {
                Text(
                    modifier = modifier,
                    text = stringResource(id = R.string.assignments_empty)
                )
            }
        }

        else -> Text(
            modifier = modifier,
            text = "Loading..."
        )
    }
}

@Composable
fun AssignmentItem(
    assignment: AssignmentUiModel,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .clickable {
                // TODO: Set up Click Listener
            }
    ) {
        val (title, arrow, divider) = createRefs()
        Text(
            modifier = modifier
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            text = assignment.name,
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
