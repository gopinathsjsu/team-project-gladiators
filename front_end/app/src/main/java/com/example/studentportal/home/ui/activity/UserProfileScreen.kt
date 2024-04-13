package com.example.studentportal.home.ui.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentportal.home.ui.viewmodel.UserProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = viewModel()) {
    val userName by viewModel.userName.observeAsState("")
    val userQualification by viewModel.userQualification.observeAsState("")
    val userEmail by viewModel.userEmail.observeAsState("")
    val userPhone by viewModel.userPhone.observeAsState("")
    val userBiography by viewModel.userBiography.observeAsState("")
    val userLinks by viewModel.userLinks.observeAsState(initial = listOf())

    Column(modifier = Modifier.padding(16.dp)) {
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold

                    )
                )
                Text(
                    text = userQualification,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = "Email")
        Text(userEmail, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = "Phone")
        Text(userPhone, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = "Biography")
        Text(userBiography, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        userLinks.takeIf { it.isNotEmpty() }?.let {
            SectionTitle(title = "Links")
            it.forEach { link ->
                Text(link, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } ?: Text("John Doe hasn’t added any links.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    )
}

@Composable
fun ProfileHeader(name: String, qualification: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        // Placeholder for the profile image. There will be an image here later.
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCircle(color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
        Text(text = qualification, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ProfileInformation(label: String, information: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = information, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ProfileLinks(links: List<String>) {
    if (links.isNotEmpty()) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Links", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            links.forEach { link ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = link, style = MaterialTheme.typography.bodyLarge)
            }
        }
    } else {
        // Display this if there are no links
        Text("No links added.", style = MaterialTheme.typography.bodyMedium)
    }
}
