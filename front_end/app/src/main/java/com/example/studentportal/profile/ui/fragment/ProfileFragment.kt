package com.example.studentportal.profile.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentProfileBinding
import com.example.studentportal.profile.ui.fragment.Model.UserProfileModel
import com.example.studentportal.profile.ui.fragment.ViewModel.UserProfileViewModel
import com.example.studentportal.profile.ui.fragment.ViewModel.ApiServiceFactory
import com.example.studentportal.profile.ui.fragment.ViewModel.UserProfileViewModelFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProfileFragment : BaseFragment<FragmentProfileBinding>(TAG) {
    private val userProfileViewModel by viewModels<UserProfileViewModel> {
        UserProfileViewModelFactory(ApiServiceFactory.createUserApiService())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            ProfileLayout(userProfileViewModel)
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
fun ProfileLayout(viewModel: UserProfileViewModel) {
    val userProfileModel by viewModel.userProfileModel.observeAsState(UserProfileModel(errorMessage = null))
    UserProfileScreen(
        userName = userProfileModel.userName,
        userQualification = userProfileModel.userQualification,
        userEmail = userProfileModel.userEmail,
        userPhone = userProfileModel.userPhone,
        userBiography = userProfileModel.userBiography,
        userLinks = userProfileModel.userLinks,
        errorMessage = userProfileModel.errorMessage
    )
}

@Composable
fun UserProfileScreen(
    userName: String,
    userQualification: String,
    userEmail: String,
    userPhone: String,
    userBiography: String,
    userLinks: List<String>,
    errorMessage: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        if (errorMessage != null) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            return
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(modifier = Modifier
                .size(100.dp)
                .weight(1f)
            ) {
                drawCircle(color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = userQualification,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileSection(title = "Email", information = userEmail)
        ProfileSection(title = "Phone", information = userPhone)
        ProfileSection(title = "Biography", information = userBiography)
        ProfileLinks(userLinks)
    }
}

@Composable
fun ProfileSection(title: String, information: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = information,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ProfileLinks(links: List<String>) {
    if (links.isNotEmpty()) {
        links.forEach { link ->
            ProfileSection(title = "Link", information = link)
        }
    } else {
        Text(
            text = "No links added.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}