package com.davirdgs.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.davirdgs.myapplication.ui.feature.login.LoginScreen
import com.davirdgs.myapplication.ui.feature.login.loginScreen
import com.davirdgs.myapplication.ui.feature.welcome.navigateToWelcomeScreen
import com.davirdgs.myapplication.ui.feature.welcome.welcomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LoginScreen
    ) {
        loginScreen(
            navigateToWelcomeScreen = navController::navigateToWelcomeScreen
        )

        welcomeScreen()
    }
}