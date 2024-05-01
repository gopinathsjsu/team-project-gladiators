import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.ui.viewmodel.GradeListViewModel

@Composable
fun GradeListLayout(
    viewModel: GradeListViewModel,
    assignmentId: String,
    userId: String
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchGrades(
            assignmentId = assignmentId,
            userId = userId
        )
    }

    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
        is BaseUiState.Success -> {
            val gradeList = uiState.data()?.grades
            if (!gradeList.isNullOrEmpty()) {
                GradeList(
                    gradeList = gradeList,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(stringResource(id = R.string.grades_empty))
            }
        }
        else -> Text(text = "Loading...")
    }
}

@Composable
fun GradeList(gradeList: List<GradeUiModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(gradeList) {
            GradeCard(
                grade = it,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun GradeCard(grade: GradeUiModel, modifier: Modifier) {
    val textStyle = TextStyle(fontSize = 22.sp)
    val scoreToRender = when (grade.score) {
        -1 -> "-"
        else -> grade.score
    }
    Box(modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = grade.studentFirstName + " " + grade.studentLastName,
                    style = textStyle
                )
                Text(
                    text = "$scoreToRender/100",
                    style = textStyle
                )
            }
            Divider()
        }
    }
}
