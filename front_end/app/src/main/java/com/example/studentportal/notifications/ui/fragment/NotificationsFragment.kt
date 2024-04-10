package com.example.studentportal.notifications.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(TAG) {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding {
        val binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            NotificationsLayout()
        }
        return binding
    }

    override fun menuItem() = R.id.nav_notifications

    companion object {

        const val TAG = "NOTIFICATIONS"
        fun newInstance(): NotificationsFragment {
            return NotificationsFragment()
        }
    }
}

@Composable
fun NotificationsLayout() {
    Text(text = "NOTIFICATIONS LAYOUT")
}
