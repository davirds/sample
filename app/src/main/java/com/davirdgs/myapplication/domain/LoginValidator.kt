package com.davirdgs.myapplication.domain

import com.davirdgs.myapplication.base.Resource

class LoginValidator {
    operator fun invoke(user: String, password: String): Resource<Unit, LoginValidatorError> =
        when {
            user.isEmpty() -> Resource.Error(LoginValidatorError.EMPTY_USER)
            password.isEmpty() -> Resource.Error(LoginValidatorError.EMPTY_PASSWORD)
            else -> Resource.Success(Unit)
        }
}

enum class LoginValidatorError {
    EMPTY_USER,
    EMPTY_PASSWORD
}