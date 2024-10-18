package com.davirdgs.myapplication

import android.app.Application
import com.davirdgs.myapplication.data.AppRepository
import com.davirdgs.myapplication.data.AppRepositoryImpl
import com.davirdgs.myapplication.data.remote.MockedService

/**
 * Description:
 *     Create a simple Android application with a login screen. The login screen should accept a username and password. Upon entering valid credentials, the user should be redirected to a welcome screen. Ensure that the login process is secure and follows best practices.
 *     Requirements: (check)
 *     Create a layout for the login screen with EditText fields for username and password, and a login button. (check)
 *     Implement validation to ensure that both username and password fields are not empty. (check)
 *     Implement a secure (not sure about what this means)
 *     secure authentication mechanism to verify the username and password. You can use hardcoded credentials for simplicity. (check)
 *     After successful authentication, navigate the user to a welcome screen displaying a welcome message (check)
 */
class App: Application() {

    val appRepository: AppRepository by lazy { AppRepositoryImpl(MockedService()) }
}