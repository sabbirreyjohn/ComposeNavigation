package com.androidrey.composenavigation.ui.view.userlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.androidrey.composenavigation.R
import com.androidrey.composenavigation.composables.ShowError
import com.androidrey.composenavigation.composables.ShowProgressBar
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.ui.theme.ComposeNavigationTheme


@Composable
fun UserListScreen(navHostController: NavHostController? = null, viewModel: UserListViewModel) {
    ComposeNavigationTheme {
        val users by viewModel.users.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val hasError by viewModel.hasError.collectAsState()
        if (isLoading) {
            ShowProgressBar()
        } else {
            if (hasError) {
                ShowError()
            } else UserList(userList = users, navHostController)
        }
    }
}

@Composable
fun UserList(userList: List<User>, navHostController: NavHostController? = null) {
    LazyColumn {
        items(userList) { userItem ->
            UserRow(userItem, navHostController)
        }
    }
}

@Composable
fun UserRow(user: User, navHostController: NavHostController? = null) {
    val context = LocalContext.current
    Card(elevation = 2.dp, modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(3.dp)
        .clickable {
            Toast
                .makeText(context, user.userName, Toast.LENGTH_LONG)
                .show()
            navHostController?.navigate("Profile?userName=${user.userName}")
        }) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberImagePainter(user.userAvatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .padding(4.dp)
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = user.userName, color = Color.Black)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewUserListScreen() {
    ComposeNavigationTheme {

    }
}