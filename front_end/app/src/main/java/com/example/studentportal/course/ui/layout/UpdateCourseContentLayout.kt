package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.studentportal.course.ui.viewmodel.UpdateCourseContentViewModel

@Composable
fun UpdateCourseContentLayout(
    viewModel: UpdateCourseContentViewModel,
    onCloseClicked: () -> Unit,
    onSubmitClicked: (String) -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    ConstraintLayout(
        Modifier.fillMaxSize()
    ) {
        val (title, input, button) = createRefs()
        DialogTitle(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            titleRes = R.string.courses_content_edit_title,
            onCloseClicked = {
                onCloseClicked.invoke()
            }
        )
        FormInput(
            modifier = Modifier
                .testTag("contentInput")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(input) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.content.orEmpty(),
            onValueChange = {
                viewModel.updateCourseContent(it)
            },
            labelStringRes = R.string.courses_menu_content
        )
        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(button) {
                    top.linkTo(input.bottom)
                    start.linkTo(parent.start)
                },
            enabled = !uiState?.content.isNullOrBlank(),
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            onClick = {
                onSubmitClicked.invoke(uiState?.content.orEmpty())
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.submit_syllabus),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
