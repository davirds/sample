package com.davirdgs.myapplication.ui.feature.welcome

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.toRoute
import com.davirdgs.myapplication.App
import com.davirdgs.myapplication.base.Resource
import com.davirdgs.myapplication.data.AppRepository
import com.davirdgs.myapplication.data.remote.User
import com.davirdgs.myapplication.domain.LoginValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val route by lazy { WelcomeScreen.from(savedStateHandle) }
    var uiState = MutableStateFlow(WelcomeUiState(route.user.name))
        private set

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return WelcomeViewModel(savedStateHandle) as T
            }
        }
    }
}

data class WelcomeUiState(val userName: String)
