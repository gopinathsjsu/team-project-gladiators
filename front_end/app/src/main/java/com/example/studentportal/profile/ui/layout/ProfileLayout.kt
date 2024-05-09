package com.example.studentportal.profile.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.R
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.ui.model.data
import com.example.studentportal.common.ui.model.error
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.ui.viewModel.UserProfileViewModel

@Composable
fun ProfileLayout(
    showLogoutButton: Boolean,
    userId: String,
    userType: UserType,
    viewModel: UserProfileViewModel,
    onLogoutClicked: () -> Unit,
    modifier: Modifier
) {
    val uiState by viewModel.uiResultLiveData.observeAsState()

    val checkedState by viewModel.checkedState.observeAsState()

    // API Call
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUserData(userId = userId)
    }

    when (uiState) {
        is BaseUiState.Error -> Text(text = uiState.error()?.message.orEmpty())
        is BaseUiState.Success -> {
            ConstraintLayout(modifier = modifier) {
                val (userLayout, logoutButton, hideAnnouncements) = createRefs()
                UserLayout(
                    user = uiState.data() ?: UserUiModel.empty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Top)
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                        .constrainAs(userLayout) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                if (showLogoutButton) {
                    if (userType != UserType.ADMIN) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .constrainAs(hideAnnouncements) {
                                    top.linkTo(userLayout.bottom)
                                    start.linkTo(parent.start)
                                }
                        ) {
                            Text(
                                text = stringResource(id = R.string.announcement_hide),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 20.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Switch(
                                checked = checkedState ?: false,
                                colors = SwitchColors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color.Black,
                                    checkedBorderColor = Color.White,
                                    checkedIconColor = Color.White,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.Gray,
                                    uncheckedBorderColor = Color.White,
                                    uncheckedIconColor = Color.White,
                                    disabledCheckedThumbColor = Color.White,
                                    disabledCheckedTrackColor = Color.White,
                                    disabledCheckedBorderColor = Color.White,
                                    disabledCheckedIconColor = Color.White,
                                    disabledUncheckedThumbColor = Color.White,
                                    disabledUncheckedTrackColor = Color.White,
                                    disabledUncheckedBorderColor = Color.White,
                                    disabledUncheckedIconColor = Color.Gray
                                ),
                                onCheckedChange = {
                                    viewModel.updateChecked(it)
                                }
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .testTag("submitButton")
                            .padding(16.dp)
                            .fillMaxWidth()
                            .constrainAs(logoutButton) {
                                top.linkTo(
                                    if (userType != UserType.ADMIN) {
                                        hideAnnouncements.bottom
                                    } else {
                                        userLayout.bottom
                                    }
                                )
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        colors = ButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray
                        ),
                        onClick = {
                            onLogoutClicked.invoke()
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(id = R.string.auth_logout),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        else -> Text(text = "Loading...")
    }
}

@Composable
fun UserLayout(
    user: UserUiModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Gray,
                            radius = this.size.maxDimension
                        )
                    },
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = user.firstName.first().toString()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        ProfileSection(title = "Email", information = user.email)
        ProfileSection(title = "Phone", information = user.phone)
        ProfileSection(title = "Biography", information = user.biography)
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
