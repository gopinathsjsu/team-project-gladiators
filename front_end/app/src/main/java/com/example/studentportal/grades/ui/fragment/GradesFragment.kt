package com.example.studentportal.grades.ui.fragment

import GradeListLayout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentGradesBinding
import com.example.studentportal.grades.ui.viewmodel.GradeListViewModel

class GradesFragment : BaseFragment<FragmentGradesBinding>(TAG) {
    internal val viewModel by viewModels<GradeListViewModel> {
        GradeListViewModel.GradeListViewModelFactory
    }

    private lateinit var assignmentId: String
    private lateinit var userId: String
    private lateinit var userType: UserType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignmentId = requireArguments().getString(KEY_ASSIGNMENT_ID) ?: throw IllegalArgumentException("Assignment ID is required")
        userId = requireArguments().getString(KEY_USER_ID) ?: throw IllegalArgumentException("User ID is required")
        userType = UserType.valueOf(requireArguments().getString(EditGradeFragment.KEY_USER_TYPE).orEmpty())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGradesBinding {
        val binding = FragmentGradesBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            GradeListLayout(
                viewModel = viewModel,
                assignmentId = assignmentId,
                userId = userId,
                onItemClick = {
                    val fragment = EditGradeFragment.newInstance(
                        grade = it,
                        userType = userType.name
                    )
                    parentFragmentManager.showBaseFragment(
                        fragment = fragment,
                        addToBackStack = true,
                        containerId = R.id.fl_content
                    )
                }
            )
        }
        return binding
    }

    override fun menuItem(): Int = -1

    companion object {
        const val KEY_ASSIGNMENT_ID = "key_course_id"
        const val KEY_USER_ID = "key_user_id"
        const val KEY_USER_TYPE = "key_user_type"
        const val TAG = "GRADES"
        fun newInstance(assignmentId: String, userId: String, userType: String): GradesFragment {
            val fragment = GradesFragment()
            val args = Bundle()
            args.putString(KEY_ASSIGNMENT_ID, assignmentId)
            args.putString(KEY_USER_ID, userId)
            args.putString(KEY_USER_TYPE, userType)
            fragment.arguments = args
            return fragment
        }
    }
}
