package com.example.studentportal.grades.ui.fragment

import EditGradeLayout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentGradesBinding
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.ui.viewmodel.EditGradeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditGradeFragment : BaseFragment<FragmentGradesBinding>(TAG) {
//    val grade = GradeUiModel(
//        gradeId = "1",
//        score = 5,
//        studentId = "1",
//        studentFirstName = "Gerardo",
//        studentLastName = "Moreno",
//        submissionLink = "www.com"
//    )

    private lateinit var grade: GradeUiModel
    private val viewModel: EditGradeViewModel by viewModel {
        parametersOf(grade)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        grade = arguments?.getParcelable(KEY_GRADE) ?: throw IllegalArgumentException("Assignment ID is required")
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGradesBinding {
        val binding = FragmentGradesBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            EditGradeLayout(
                viewModel = viewModel
            )
        }
        return binding
    }

    override fun menuItem(): Int = -1

    companion object {
        const val KEY_GRADE = "key_grade"
        const val TAG = "EDIT_GRADE"

        fun newInstance(grade: GradeUiModel): EditGradeFragment {
            val fragment = EditGradeFragment()
            val args = Bundle()
            args.putParcelable(EditGradeFragment.KEY_GRADE, grade)
            fragment.arguments = args
            return fragment
        }
    }
}
