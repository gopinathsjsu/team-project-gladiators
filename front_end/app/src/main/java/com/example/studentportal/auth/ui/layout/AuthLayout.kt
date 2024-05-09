package com.example.studentportal.auth.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.auth.ui.viewModel.AuthViewModel
import com.example.studentportal.common.ui.layout.DialogTitle
import com.example.studentportal.common.ui.layout.FormInput
import com.example.studentportal.common.ui.model.isError
import com.example.studentportal.common.ui.model.isLoading

@Composable
fun AuthLayout(
    modifier: Modifier,
    viewModel: AuthViewModel,
    onSubmitClicked: () -> Unit
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    ConstraintLayout(modifier) {
        val (title, usernameInput, passwordInput, submitButton, errorText) = createRefs()
        DialogTitle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            titleRes = R.string.auth_title,
            onCloseClicked = {},
            showCloseButton = false
        )
        FormInput(
            modifier = Modifier
                .testTag("usernameInput")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(usernameInput) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.data?.usernameInput.orEmpty(),
            onValueChange = {
                viewModel.updateUsername(it)
            },
            labelStringRes = R.string.auth_username,
            enabled = uiState?.isLoading() != true
        )
        FormInput(
            modifier = Modifier
                .testTag("linkInput")
                .padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                .fillMaxWidth()
                .constrainAs(passwordInput) {
                    top.linkTo(usernameInput.bottom)
                    start.linkTo(parent.start)
                },
            value = uiState?.data?.passwordInput.orEmpty(),
            onValueChange = {
                viewModel.updatePassword(it)
            },
            labelStringRes = R.string.auth_password,
            trailingIcon = {
                val isPasswordVisible = viewModel.uiResultLiveData.value?.data?.passwordInputVisible ?: false
                PasswordVisibilityToggle(isPasswordVisible) {
                    viewModel.updatePasswordVisible(isPasswordVisible = !isPasswordVisible)
                }
            },
            visualTransformation = if (viewModel.uiResultLiveData.value?.data?.passwordInputVisible == true) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            enabled = uiState?.isLoading() != true
        )
        if (uiState?.isError() == true) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(errorText) {
                        top.linkTo(passwordInput.bottom)
                        start.linkTo(parent.start)
                    },
                color = Color.Red,
                text = uiState?.error?.message.orEmpty(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        }
        Button(
            modifier = Modifier
                .testTag("submitButton")
                .padding(16.dp)
                .fillMaxWidth()
                .constrainAs(submitButton) {
                    top.linkTo(
                        if (uiState?.isError() == true) {
                            errorText.bottom
                        } else {
                            passwordInput.bottom
                        }
                    )
                    start.linkTo(parent.start)
                },
            enabled = uiState?.data?.readyToSubmit() == true && uiState?.isLoading() != true,
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            onClick = {
                uiState?.let {
                    onSubmitClicked.invoke()
                }
            }
        ) {
            if (uiState?.isLoading() == true) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                        .padding(8.dp),
                    color = Color.White
                )
            } else {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.auth_submit),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PasswordVisibilityToggle(passwordVisible: Boolean, onClick: () -> Unit) {
    val text = if (passwordVisible) {
        stringResource(id = R.string.auth_hide_password)
    } else {
        stringResource(id = R.string.auth_show_password)
    }
    val color = if (passwordVisible) Color.Gray else Color.Black
    Text(
        text = text,
        color = color,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    )
}
