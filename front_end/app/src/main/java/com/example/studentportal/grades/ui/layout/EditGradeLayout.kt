import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentportal.R
import com.example.studentportal.common.ui.layout.FormInput
import com.example.studentportal.grades.ui.viewmodel.EditGradeViewModel

@Composable
fun EditGradeLayout(
    viewModel: EditGradeViewModel
) {
    val grade by viewModel.grade.collectAsState()

    val scoreToRender = if (grade.score == -1) "-" else grade.score
    if (viewModel.showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.dismissDialog()
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(viewModel.dialogMessage)
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.dismissDialog()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Gray,
                            radius = this.size.maxDimension
                        )
                    },
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = grade.studentFirstName.first().toString()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "${grade.studentFirstName} ${grade.studentLastName}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        GradeSection(title = "Score", information = "$scoreToRender/100")
        GradeSection(title = "Submission Link", information = grade.submissionLink)
        EditGradeSection(viewModel = viewModel)
    }
}

@Composable
fun EditGradeSection(
    viewModel: EditGradeViewModel
) {
    val userType = viewModel.userType
    val text by viewModel.text.collectAsState()

    val labelStringRes = when (userType) {
        "STUDENT" -> R.string.post_submission_link_label
        else -> R.string.edit_score_label
    }

    val buttonStringRes = when (userType) {
        "STUDENT" -> R.string.post_submission_link_button
        else -> R.string.edit_score_button
    }

    val keyboardOptions = when (userType) {
        "STUDENT" -> KeyboardOptions(keyboardType = KeyboardType.Text)
        else -> KeyboardOptions(keyboardType = KeyboardType.Number)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FormInput(
            modifier = Modifier
                .testTag("nameInput")
                .padding(16.dp)
                .fillMaxWidth(),
            value = text,
            keyboardOptions = keyboardOptions,
            onValueChange = { viewModel.updateText(it) },
            labelStringRes = labelStringRes
        )
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            onClick = { viewModel.onButtonClick() }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(buttonStringRes),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun GradeSection(title: String, information: String?) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = information ?: "No submission",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
