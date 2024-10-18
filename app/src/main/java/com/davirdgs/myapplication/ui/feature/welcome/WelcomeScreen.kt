package com.davirdgs.myapplication.ui.feature.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.davirdgs.myapplication.R
import com.davirdgs.myapplication.base.serializableType
import com.davirdgs.myapplication.data.remote.User
import com.davirdgs.myapplication.ui.feature.login.LoginScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class WelcomeScreen(val user: User) {
    companion object {
        val typeMap = mapOf(typeOf<User>() to serializableType<User>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<WelcomeScreen>(typeMap)
    }
}

fun NavController.navigateToWelcomeScreen(user: User) {
    this.navigate(WelcomeScreen(user)) {
        launchSingleTop = true
        popUpTo<LoginScreen> {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.welcomeScreen() {
    composable<WelcomeScreen>(
        typeMap = WelcomeScreen.typeMap
    ) {
        val viewModel = viewModel<WelcomeViewModel>(factory = WelcomeViewModel.factory)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        WelcomeScreen(uiState = uiState)
    }
}

@Composable
internal fun WelcomeScreen(
    modifier: Modifier = Modifier,
    uiState: WelcomeUiState,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.welcome_screen_title, uiState.userName),
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF3F4EF
)
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(uiState = WelcomeUiState("Davi"))
}