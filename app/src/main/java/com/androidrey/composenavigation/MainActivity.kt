package com.androidrey.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.androidrey.composenavigation.model.User
import com.androidrey.composenavigation.ui.theme.ComposeNavigationTheme
import com.androidrey.composenavigation.ui.view.profile.ProfileScreen
import com.androidrey.composenavigation.ui.view.userlist.UserListScreen
import com.androidrey.composenavigation.ui.view.userlist.UserListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    Column {
        TopAppBar(
            elevation = 2.dp,
            title = { Text(text = "Github Buddy") }
        )
        val navController = rememberNavController()
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "UserList") {
                composable("UserList") {
                    UserListScreen(navController)
                }
                composable("Profile?userName={userName}", arguments = listOf(
                    navArgument("userName") {
                        type = NavType.StringType
                        defaultValue = "Sabbir"
                    }

                )) {
                    val user = it.arguments?.getString("userName")
                    ProfileScreen(user)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainContent() {
    MainContent()
}