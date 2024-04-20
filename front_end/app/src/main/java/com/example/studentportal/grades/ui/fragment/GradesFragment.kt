package com.example.studentportal.grades.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
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
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.databinding.FragmentGradesBinding
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.ui.viewmodel.GradeListViewModel

class GradesFragment : BaseFragment<FragmentGradesBinding>(TAG) {
    internal val viewModel by viewModels<GradeListViewModel> {
        GradeListViewModel.GradeListViewModelFactory
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGradesBinding {
        val binding = FragmentGradesBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            GradeListLayout(viewModel = viewModel)
        }
        return binding
    }

    override fun menuItem() = R.id.nav_grades

    companion object {

        const val TAG = "GRADES"
        fun newInstance(): GradesFragment {
            return GradesFragment()
        }
    }
}

@Composable
fun GradeListLayout(viewModel: GradeListViewModel) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        print("Hello World!")
        viewModel.fetchGrades(assignmentId = "c665e126-846c-4375-989d-d963053762a3")
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
                    text = "${grade.score}/100",
                    style = textStyle
                )
            }
            Divider()
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun GradeListPreview() {
//    val grades = MockGradeListDataSource
//        .getMockGradeList()
//        .toUiModel()
//        .grades
//    GradeList(
//        gradeList = grades,
//        modifier = Modifier.fillMaxSize()
//    )
// }
