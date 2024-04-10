package com.example.studentportal.profile.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(TAG) {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ProfileLayout()
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

@Composable
fun ProfileLayout() {
    Text(text = "PROFILE LAYOUT")
}
