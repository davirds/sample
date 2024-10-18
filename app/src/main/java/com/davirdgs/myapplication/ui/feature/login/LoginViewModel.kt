package com.davirdgs.myapplication.ui.feature.login

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.davirdgs.myapplication.App
import com.davirdgs.myapplication.R
import com.davirdgs.myapplication.base.Resource
import com.davirdgs.myapplication.data.AppRepository
import com.davirdgs.myapplication.data.NetworkError
import com.davirdgs.myapplication.data.remote.User
import com.davirdgs.myapplication.domain.LoginValidator
import com.davirdgs.myapplication.domain.LoginValidatorError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: AppRepository,
    private val loginValidator: LoginValidator
): ViewModel() {
    var uiState = MutableStateFlow(LoginUiState())
        private set

    fun setUser(user: String) {
        uiState.update { it.copy(user = user, error = null) }
    }

    fun setPassword(password: String) {
        uiState.update { it.copy(password = password, error = null) }
    }

    fun submit(onSuccess: (User) -> Unit) {
        uiState.update { it.copy(showLoading = true) }

        val user = uiState.value.user
        val password = uiState.value.password

        when(val validation = loginValidator(user, password)) {
            is Resource.Success -> requestLogin(user, password, onSuccess)
            is Resource.Error ->
                uiState.update {
                    it.copy(
                        showLoading = false,
                        error = validation.error.errorMessage()
                    )
                }
        }
    }

    private fun requestLogin(user: String, password: String, onSuccess: (User) -> Unit) {
        viewModelScope.launch {
            repository.login(user, password)
                .collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            uiState.update { it.copy(showLoading = false) }
                            onSuccess(result.data)
                        }
                        is Resource.Error ->
                            uiState.update {
                                it.copy(
                                    showLoading = false,
                                    error = result.error.errorMessage()
                                )
                            }
                    }
                }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val repository = (application as App).appRepository
                val loginValidator = LoginValidator()
                return LoginViewModel(repository, loginValidator) as T
            }
        }
    }
}


data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val showLoading: Boolean = false,
    @StringRes
    val error: Int? = null
) {
    val showError: Boolean
        get() = error != null
}

@StringRes
private fun LoginValidatorError.errorMessage(): Int =
    when(this) {
        LoginValidatorError.EMPTY_USER -> R.string.login_screen_error_user_empty
        LoginValidatorError.EMPTY_PASSWORD -> R.string.login_screen_error_password_empty
    }

@StringRes
private fun NetworkError.errorMessage(): Int =
    when(this) {
        NetworkError.UNAUTHORIZED -> R.string.login_screen_error_user_unautorized
        NetworkError.NO_INTERNET -> R.string.app_error_not_internet_connection
    }