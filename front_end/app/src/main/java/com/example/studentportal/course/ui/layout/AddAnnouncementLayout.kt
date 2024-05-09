package com.example.studentportal.course.ui.layout

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
import com.example.studentportal.common.ui.layout.DialogTitle
import com.example.studentportal.common.ui.layout.FormInput
import com.example.studentportal.course.ui.model.AnnouncementUiModel
import com.example.studentportal.course.ui.viewmodel.AddAnnouncementViewModel

@Composable
fun AddAnnouncementLayout(
    courseId: String,
    viewModel: AddAnnouncementViewModel,
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onSubmitClicked: (AnnouncementUiModel) -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    ConstraintLayout(modifier = modifier) {
        val (title, titleInput, messageInput, submitButton) = createRefs()
        DialogTitle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            titleRes = R.string.add_announcement_title,
            onCloseClicked = onCloseClicked
        )
        FormInput(
            modifier = Modifier
                .testTag("nameInput")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(titleInput) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.title.orEmpty(),
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
                .constrainAs(messageInput) {
                    top.linkTo(titleInput.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.description.orEmpty(),
            onValueChange = {
                viewModel.updateDescription(it)
            },
            labelStringRes = R.string.announcement_message
        )
        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(submitButton) {
                    top.linkTo(messageInput.bottom)
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
