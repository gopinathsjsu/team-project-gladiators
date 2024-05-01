package com.example.studentportal.common.ui.layout

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    modifier: Modifier,
    value: String,
    @StringRes labelStringRes: Int,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black, // Highlight color when the text field is focused
            unfocusedBorderColor = Color.Gray, // Highlight color when the text field is not focused
            cursorColor = Color.Black // Color of the cursor
        ),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = labelStringRes),
                color = Color.Black
            )
        },
        enabled = enabled
    )
}
