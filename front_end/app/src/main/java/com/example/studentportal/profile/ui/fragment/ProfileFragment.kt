package com.example.studentportal.profile.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentProfileBinding
import com.example.studentportal.profile.ui.layout.ProfileLayout
import com.example.studentportal.profile.ui.viewModel.UserProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(TAG) {
    internal val viewModel by viewModels<UserProfileViewModel> {
        UserProfileViewModel.UserProfileViewModelFactory
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ProfileLayout("23da5a0a-905c-41f9-9595-aeff08411fb8", viewModel)
        }
        return binding
    }

    override fun menuItem() = R.id.nav_profile

    companion object {
        const val TAG = "PROFILE"
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
