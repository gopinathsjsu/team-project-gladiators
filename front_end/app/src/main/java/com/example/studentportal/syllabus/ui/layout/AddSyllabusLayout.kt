package com.example.studentportal.syllabus.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studentportal.R
import com.example.studentportal.syllabus.ui.viewModel.AddSyllabusViewModel
import com.example.studentportal.syllabus.ui.viewModel.SyllabusState

@Composable
fun AddSyllabusLayout(
    viewModel: AddSyllabusViewModel,
    onCloseClicked: () -> Unit,
    onSubmitClicked: (String) -> Unit
) {
    val syllabusState by viewModel.uiResultLiveData.observeAsState(SyllabusState.INITIAL)
    var syllabusText by remember { mutableStateOf(syllabusState.syllabus) }

    LaunchedEffect(syllabusState.syllabus) {
        syllabusText = syllabusState.syllabus
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_syllabus),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = syllabusText,
            onValueChange = {
                viewModel.updateSyllabus(it)
            },
            label = { Text(stringResource(id = R.string.syllabus_details)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("syllabusInput")
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                onSubmitClicked(syllabusText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("addSyllabusButton"),
            enabled = syllabusState.readyToSubmit(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.submit_syllabus),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
