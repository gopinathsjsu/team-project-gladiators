package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.studentportal.common.ui.layout.DropdownInput
import com.example.studentportal.common.ui.layout.FormInput
import com.example.studentportal.course.ui.viewmodel.CourseInputViewModel
import com.example.studentportal.home.ui.model.BaseCourseUiModel

@Composable
fun CourseInputLayout(
    existingCourse: BaseCourseUiModel.CourseUiModel?,
    viewModel: CourseInputViewModel,
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onSubmitClicked: (BaseCourseUiModel.CourseUiModel) -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchInputData()
    }
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, nameInput, descriptionInput, semesterInput, instructorInput, submitButton) = createRefs()
        DialogTitle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            titleRes = if (existingCourse != null) {
                R.string.courses_update
            } else {
                R.string.courses_new
            },
            onCloseClicked = onCloseClicked
        )
        FormInput(
            modifier = Modifier
                .testTag("nameInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp))
                .fillMaxWidth()
                .constrainAs(nameInput) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            enabled = existingCourse == null,
            value = uiState?.name.orEmpty(),
            onValueChange = {
                viewModel.updateName(it)
            },
            labelStringRes = R.string.courses_name
        )
        FormInput(
            modifier = Modifier
                .testTag("descriptionInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(descriptionInput) {
                    top.linkTo(nameInput.bottom)
                    start.linkTo(parent.start)
                },
            enabled = existingCourse == null,
            value = uiState?.description.orEmpty(),
            onValueChange = {
                viewModel.updateDescription(it)
            },
            labelStringRes = R.string.courses_menu_content
        )
        DropdownInput(
            modifier = Modifier
                .testTag("semesterInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(semesterInput) {
                    top.linkTo(descriptionInput.bottom)
                    start.linkTo(parent.start)
                },
            enabled = existingCourse == null,
            options = uiState?.semesters.orEmpty(),
            onClick = { option ->
                viewModel.updateSelectedSemester(option)
            },
            labelStringRes = R.string.courses_semester,
            selectedItem = uiState?.selectedSemester
        )
        DropdownInput(
            modifier = Modifier
                .testTag("instructorInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(instructorInput) {
                    top.linkTo(semesterInput.bottom)
                    start.linkTo(parent.start)
                },
            options = uiState?.users.orEmpty(),
            onClick = { option ->
                viewModel.updateSelectedInstructor(option)
            },
            labelStringRes = R.string.courses_instructor,
            selectedItem = uiState?.selectedUser
        )
        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp))
                .fillMaxWidth()
                .constrainAs(submitButton) {
                    top.linkTo(instructorInput.bottom)
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
                viewModel.uiResultLiveData.value?.toUiModel(
                    id = existingCourse?.id,
                    assignments = existingCourse?.assignments.orEmpty(),
                    enrolledStudents = existingCourse?.enrolledStudents.orEmpty()
                )?.let {
                    onSubmitClicked.invoke(it)
                }
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = if (existingCourse != null) {
                    stringResource(id = R.string.courses_update)
                } else {
                    stringResource(id = R.string.courses_new)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
