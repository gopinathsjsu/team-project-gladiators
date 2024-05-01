package com.example.studentportal.assignment.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.studentportal.R
import com.example.studentportal.assignment.ui.layout.AssignmentListLayout
import com.example.studentportal.assignment.ui.model.AssignmentUiModel
import com.example.studentportal.assignment.ui.viewmodel.AssignmentsViewModel
import com.example.studentportal.common.di.getUserId
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.showBaseDialogFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.FragmentAssignmentsBinding
import com.example.studentportal.grades.ui.fragment.GradesFragment
import org.koin.android.ext.android.inject

class AssignmentsFragment(
    viewModelFactory: ViewModelProvider.Factory = AssignmentsViewModel.AssignmentsViewModelFactory
) : BaseFragment<FragmentAssignmentsBinding>(TAG) {

    internal val viewModel by viewModels<AssignmentsViewModel> { viewModelFactory }

    // Inject SharedPreferences
    private val sharedPreferences: SharedPreferences by inject()

    val courseId: String
        get() = requireArguments().getString(KEY_COURSE_ID).orEmpty()

    val userType: UserType
        get() = UserType.valueOf(requireArguments().getString(KEY_USER_TYPE).orEmpty())

    val userId: String = sharedPreferences.getUserId()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignmentsBinding {
        val binding = FragmentAssignmentsBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            AssignmentListLayout(
                modifier = Modifier.fillMaxSize(),
                courseId = courseId,
                userType = userType,
                viewModel = viewModel,
                onAddClicked = {
                    childFragmentManager.showBaseDialogFragment(
                        AddAssignmentFragment.newInstance(courseId)
                    )
                },
                onItemClick = {
                    val fragment = GradesFragment.newInstance(
                        it,
                        userId = userId
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener(AddAssignmentFragment.KEY_FRAGMENT_RESULT, this) { key, bundle ->
            bundle.getParcelable<AssignmentUiModel>(AddAssignmentFragment.KEY_NEW_ASSIGNMENT)?.let {
                viewModel.createNewAssignment(
                    assignmentUiModel = it,
                    onError = {
                        Toast.makeText(
                            requireContext(),
                            it.error?.message.orEmpty(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }

    override fun menuItem(): Int = -1

    companion object {
        const val TAG = "ASSIGNMENTS"
        const val KEY_COURSE_ID = "KET_COURSE_ID"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        fun newInstance(courseId: String, userType: String): AssignmentsFragment {
            val fragment = AssignmentsFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE_ID to courseId,
                KEY_USER_TYPE to userType
            )
            return fragment
        }
    }
}
