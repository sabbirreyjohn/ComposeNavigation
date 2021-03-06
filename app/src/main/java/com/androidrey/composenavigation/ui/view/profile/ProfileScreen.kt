package com.androidrey.composenavigation.ui.view.profile

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.androidrey.composenavigation.composables.ShowError
import com.androidrey.composenavigation.model.Profile

@Composable
fun ProfileScreen(userName: String? = null, viewModel: ProfileViewModel) {
    val profile by viewModel.profile.collectAsState()
    val hasError by viewModel.hasError.collectAsState()

    LaunchedEffect(profile) {
        viewModel.loadProfile(userName)
    }

    if (!hasError) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .border(width = 1.dp, color = Color.Black)
                .padding(12.dp)
        ) {
            if (hasError)
                ShowError()
            else
                ShowProfileCard(profile)
        }
    }
}

@Composable
fun ShowProfileCard(profile: Profile) {
    Row {
        Image(
            painter = rememberImagePainter(profile.avatar_url),
            contentDescription = "Launcher",
            modifier = Modifier.size(128.dp)
        )
        Column(modifier = Modifier.padding(4.dp)) {
            profile.name?.let { Text(text = it, color = Color.Black) }
            profile.email?.let { Text(text = it, color = Color.Black) }
            profile.location?.let { Text(text = it, color = Color.Black) }
            profile.bio?.let { Text(text = it, color = Color.Black) }
            profile.blog?.let { Text(text = it, color = Color.Black) }
        }
    }
}