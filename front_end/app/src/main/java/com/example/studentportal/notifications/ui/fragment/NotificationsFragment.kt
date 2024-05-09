package com.example.studentportal.notifications.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.studentportal.R
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.databinding.FragmentNotificationsBinding
import com.example.studentportal.notifications.ui.model.NotificationType
import com.example.studentportal.notifications.ui.model.NotificationUiModel
import com.example.studentportal.notifications.ui.viewmodel.NotificationListViewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(TAG) {
    internal val viewModel by viewModels<NotificationListViewModel> {
        NotificationListViewModel.NotificationListViewModelFactory
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding {
        val binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            NotificationListLayout(viewModel = viewModel)
        }
        return binding
    }

    // override fun menuItem() = R.id.nav_notifications

    override fun menuItem() = -1

    companion object {

        const val TAG = "NOTIFICATIONS"
        fun newInstance(): NotificationsFragment {
            return NotificationsFragment()
        }
    }
}

@Composable
fun NotificationListLayout(viewModel: NotificationListViewModel) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchNotifications()
    }

    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
        is BaseUiState.Success -> {
            val notificationList = uiState.data()?.notifications
            if (!notificationList.isNullOrEmpty()) {
                NotificationList(
                    notificationList = notificationList,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(stringResource(id = R.string.empty_notifications))
            }
        }
        else -> Text(text = "Loading...")
    }
}

@Composable
fun NotificationList(notificationList: List<NotificationUiModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(notificationList) {
            NotificationCard(
                notification = it,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationUiModel, modifier: Modifier) {
    val message = when (notification.type) {
        NotificationType.ANNOUNCEMENT_CREATED -> stringResource(id = R.string.announcement_created)
        NotificationType.ASSIGNMENT_CREATED -> stringResource(id = R.string.assignment_created)
        NotificationType.ASSIGNMENT_GRADED -> stringResource(id = R.string.assignment_graded)
    }
    val textStyle = TextStyle(fontSize = 22.sp)

    Box(modifier) {
        Column {
            Text(
                text = message,
                style = textStyle
            )
            Text(
                text = "${notification.courseName}: ${notification.eventTitle}",
                style = textStyle
            )
            Divider()
        }
    }
}
