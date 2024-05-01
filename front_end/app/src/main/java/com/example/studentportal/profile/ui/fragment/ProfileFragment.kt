package com.example.studentportal.profile.ui.fragment

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.auth.ui.showLogoutDialog
import com.example.studentportal.common.di.clearAuthenticatedUserData
import com.example.studentportal.common.di.getUserId
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentProfileBinding
import com.example.studentportal.profile.ui.layout.ProfileLayout
import com.example.studentportal.profile.ui.viewModel.UserProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(TAG) {
    internal val viewModel by viewModels<UserProfileViewModel> {
        UserProfileViewModel.UserProfileViewModelFactory
    }

    val userId: String
        get() = arguments?.getString(KEY_USER_ID).orEmpty()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ProfileLayout(
                showLogoutButton = userId == koin.get<SharedPreferences>().getUserId(),
                userId = userId,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onLogoutClicked = {
                    showLogoutDialog(requireActivity()) {
                        koin.get<SharedPreferences>().clearAuthenticatedUserData() // Clear JwtToken
                        requireActivity().finish()
                    }
                }
            )
        }
        return binding
    }

    override fun menuItem() = R.id.nav_profile

    companion object {
        const val TAG = "PROFILE"
        const val KEY_USER_ID = "KEY_USER_ID"
        fun newInstance(userId: String): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = bundleOf(
                KEY_USER_ID to userId
            )
            return fragment
        }
    }
}
