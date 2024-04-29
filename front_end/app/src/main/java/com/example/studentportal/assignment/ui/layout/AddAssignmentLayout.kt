package com.example.studentportal.assignment.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.assignment.ui.viewmodel.AddAssignmentViewModel
import com.example.studentportal.common.ui.layout.DateInput
import com.example.studentportal.common.ui.layout.DialogTitle
import com.example.studentportal.common.ui.layout.FormInput

@Composable
fun AddAssignmentLayout(
    courseId: String,
    viewModel: AddAssignmentViewModel,
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onSubmitClicked: (AssignmentUiModel) -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    ConstraintLayout(modifier = modifier) {
        val (title, nameInput, linkInput, dateInput, submitButton) = createRefs()
        DialogTitle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            titleRes = R.string.assignments_new,
            onCloseClicked = onCloseClicked
        )
        FormInput(
            modifier = Modifier
                .testTag("nameInput")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(nameInput) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.name.orEmpty(),
            onValueChange = {
                viewModel.updateName(it)
            },
            labelStringRes = R.string.assignments_name
        )
        FormInput(
            modifier = Modifier
                .testTag("linkInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(linkInput) {
                    top.linkTo(nameInput.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.link.orEmpty(),
            onValueChange = {
                viewModel.updateLink(it)
            },
            labelStringRes = R.string.assignments_link
        )
        DateInput(
            modifier = Modifier
                .testTag("dateInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(dateInput) {
                    top.linkTo(linkInput.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.dueDate.orEmpty(),
            labelStringRes = R.string.assignments_due_date,
            onValueChange = {
                viewModel.updateDate(it)
            }
        )
        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(submitButton) {
                    top.linkTo(dateInput.bottom)
                    start.linkTo(parent.start)
                },
            enabled = uiState?.readyToSubmit() ?: false,
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            onClick = {
                uiState?.let {
                    onSubmitClicked.invoke(it.toUiModel(courseId))
                }
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.assignments_create_new),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
