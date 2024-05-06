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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.ui.viewmodel.EditGradeViewModel

@Composable
fun EditGradeLayout(
    viewModel: EditGradeViewModel,
    grade: GradeUiModel,
    userType: UserType
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.updateScore(grade.score.toString())
        viewModel.updateSubmissionLink(grade.submissionLink.toString())
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
        GradeSection(
            title = stringResource(R.string.edit_grade_score_label),
            information = "${
            if ((uiState?.score?.toIntOrNull() ?: -1) == -1) {
                GradeUiModel.DEFAULT_SCORE_STRING
            } else {
                uiState?.score
            }
            }/100"
        )
        GradeSection(title = stringResource(R.string.edit_grade_submission_link_label), information = uiState?.submissionLink.toString())
        EditGradeSection(viewModel = viewModel, grade = grade, userType = userType)
    }
}

@Composable
fun EditGradeSection(
    grade: GradeUiModel,
    viewModel: EditGradeViewModel,
    userType: UserType
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    val labelStringRes = when (userType) {
        UserType.STUDENT -> R.string.post_submission_link_label
        else -> R.string.edit_score_label
    }

    val buttonStringRes = when (userType) {
        UserType.STUDENT -> R.string.post_submission_link_button
        else -> R.string.edit_score_button
    }

    val keyboardOptions = when (userType) {
        UserType.STUDENT -> KeyboardOptions(keyboardType = KeyboardType.Text)
        else -> KeyboardOptions(keyboardType = KeyboardType.Number)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        uiState?.let { state ->
            FormInput(
                modifier = Modifier
                    .testTag("nameInput")
                    .padding(16.dp)
                    .fillMaxWidth(),
                value = state.text,
                keyboardOptions = keyboardOptions,
                onValueChange = { viewModel.updateText(it) },
                labelStringRes = labelStringRes
            )
        }
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
            onClick = { viewModel.onButtonClick(initialGrade = grade, userType = userType) }
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
fun GradeSection(title: String, information: String) {
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
            text = if (information == "") stringResource(R.string.no_submission) else information,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
