package com.example.studentportal.grades.ui.fragment

import GradeListLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentGradesBinding
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