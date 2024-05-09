package com.example.studentportal.profile.ui.layout

import androidx.compose.foundation.layout.PaddingValues
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
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.ui.viewModel.EditProfileViewModel

@Composable
fun EditProfileLayout(
    existingUser: UserUiModel?,
    viewModel: EditProfileViewModel,
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onSubmitClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, biographyInput, phone, email, submitButton) = createRefs()
        DialogTitle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            titleRes = R.string.profile_edit_title,
            onCloseClicked = onCloseClicked
        )
        FormInput(
            modifier = Modifier
                .testTag("biographyInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp))
                .fillMaxWidth()
                .constrainAs(biographyInput) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.biography.orEmpty(),
            onValueChange = {
                viewModel.updateBiography(it)
            },
            labelStringRes = R.string.profile_biography
        )
        FormInput(
            modifier = Modifier
                .testTag("phoneInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(phone) {
                    top.linkTo(biographyInput.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.phone.orEmpty(),
            onValueChange = {
                viewModel.updatePhone(it)
            },
            labelStringRes = R.string.profile_phone
        )
        FormInput(
            modifier = Modifier
                .testTag("emailInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(email) {
                    top.linkTo(phone.bottom)
                    start.linkTo(parent.start)
                },
            enabled = false,
            value = uiState?.email.orEmpty(),
            onValueChange = {
                viewModel.updateEmail(it)
            },
            labelStringRes = R.string.profile_email
        )

        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp))
                .fillMaxWidth()
                .constrainAs(submitButton) {
                    top.linkTo(email.bottom)
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
                onSubmitClicked.invoke()
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.auth_submit),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
