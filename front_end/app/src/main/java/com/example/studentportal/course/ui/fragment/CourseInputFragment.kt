package com.example.studentportal.course.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.course.ui.layout.CourseInputLayout
import com.example.studentportal.course.ui.viewmodel.CourseInputViewModel
import com.example.studentportal.databinding.FragmentAddCourseBinding
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import kotlinx.coroutines.Dispatchers

class CourseInputFragment : BaseDialogFragment<FragmentAddCourseBinding>(TAG) {

    internal val viewModel by viewModels<CourseInputViewModel> {
        CourseInputViewModel.ViewModelProviderFactory(
            dispatcher = Dispatchers.IO,
            courseUiModel = existingCourse
        )
    }

    private val existingCourse: BaseCourseUiModel.CourseUiModel?
        get() = arguments?.getParcelable(KEY_COURSE)

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddCourseBinding {
        val binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            CourseInputLayout(
                existingCourse = existingCourse,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onCloseClicked = {
                    dialog?.dismiss()
                },
                onSubmitClicked = { course ->
                    setFragmentResult(
                        KEY_FRAGMENT_RESULT,
                        bundleOf(
                            KEY_COURSE_INPUT to course
                        )
                    )
                    dismiss()
                }
            )
        }
        return binding
    }

    companion object {
        const val TAG = "ADD_COURSE"
        const val KEY_COURSE = "KEY_COURSE"
        const val KEY_FRAGMENT_RESULT = "KEY_FRAGMENT_RESULT"
        const val KEY_COURSE_INPUT = "KEY_COURSE_INPUT"

        fun newInstance(course: BaseCourseUiModel? = null): CourseInputFragment {
            val fragment = CourseInputFragment()
            fragment.arguments = bundleOf(
                KEY_COURSE to course
            )
            return fragment
        }
    }
}
