package com.example.studentportal.grades.ui.fragment

import EditGradeLayout
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.studentportal.common.di.getUserType
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentGradesBinding
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.ui.viewmodel.EditGradeViewModel
import com.example.studentportal.grades.ui.viewmodel.GradeListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditGradeFragment : BaseFragment<FragmentGradesBinding>(TAG) {

    internal val viewModel by viewModels<EditGradeViewModel> {
        EditGradeViewModel.EditGradeViewModelFactory
    }

    private lateinit var grade: GradeUiModel
    private lateinit var userType: UserType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        grade = arguments?.getParcelable(KEY_GRADE) ?: throw IllegalArgumentException("Assignment ID is required")
        userType = UserType.valueOf(requireArguments().getString(KEY_USER_TYPE).orEmpty())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGradesBinding {
        val binding = FragmentGradesBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            EditGradeLayout(
                viewModel = viewModel,
                grade = grade,
                userType = userType,
            )
        }
        return binding
    }

    override fun menuItem(): Int = -1

    companion object {
        const val KEY_GRADE = "key_grade"
        const val KEY_USER_TYPE = "key_user_type"
        const val TAG = "EDIT_GRADE"

        fun newInstance(grade: GradeUiModel, userType: String): EditGradeFragment {
            val fragment = EditGradeFragment()
            val args = Bundle()
            args.putParcelable(KEY_GRADE, grade)
            args.putString(KEY_USER_TYPE, userType)
            fragment.arguments = args
            return fragment
        }
    }
}
