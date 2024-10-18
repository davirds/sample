package com.davirdgs.myapplication.ui.feature.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.davirdgs.myapplication.R
import com.davirdgs.myapplication.data.remote.User
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen

fun NavGraphBuilder.loginScreen(
    navigateToWelcomeScreen: (User) -> Unit,
) {
    composable<LoginScreen> {
        val viewModel = viewModel<LoginViewModel>(factory = LoginViewModel.factory)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LoginScreen(
            uiState = uiState,
            onUserChanged = viewModel::setUser,
            onPasswordChanged = viewModel::setPassword,
            onSubmit = { viewModel.submit(navigateToWelcomeScreen) }
        )
    }
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onUserChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.showLoading,
            value = uiState.user,
            isError = uiState.showError,
            placeholder = {
                Text(text = stringResource(R.string.login_screen_login_placeholder))
            },
            onValueChange = onUserChanged
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.showLoading,
            placeholder = {
                Text(text = stringResource(R.string.login_screen_password_placeholder))
            },
            isError = uiState.showError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = uiState.password,
            onValueChange = onPasswordChanged
        )
        AnimatedContent(
            uiState.error,
            label = "error-animation",
        ) { error ->
            error?.let {
                Text(
                    text = stringResource(it),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
        }
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            label = stringResource(R.string.login_screen_submit_button),
            showLoading = uiState.showLoading,
            onClick = onSubmit
        )
    }
}

@Composable
private fun CustomButton(
    modifier: Modifier = Modifier,
    showLoading: Boolean = false,
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(56.dp),
        content = {
            AnimatedContent(
                showLoading,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "button-loading-animation"
            ) { loading ->
                when(loading) {
                    false -> Text(label)
                    true -> CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        },
        onClick = onClick
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF3F4EF
)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(showLoading = true),
        onUserChanged = {},
        onPasswordChanged = {},
        onSubmit = {}
    )
}

