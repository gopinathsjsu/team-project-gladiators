package com.example.studentportal.home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.studentportal.home.ui.viewmodel.UserViewModel

class UserFragment: Fragment() {
    val viewModel by viewModels<UserViewModel> {
        UserViewModel.StudentViewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchStudent("whatever")
    }
    
    companion object {

        const val TAG = "UserFragment"
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }
}