package com.davirdgs.myapplication.data

import com.davirdgs.myapplication.base.Resource
import com.davirdgs.myapplication.data.remote.AppService
import com.davirdgs.myapplication.data.remote.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface AppRepository {
    fun login(user: String, password: String): Flow<Resource<User, NetworkError>>
}

internal class AppRepositoryImpl(
    private val service: AppService
): AppRepository {

    override fun login(user: String, password: String) = flow {
        val result: Resource<User, NetworkError> =
            runCatching {
                service.login(user, password)
            }.fold(
                onSuccess = { Resource.Success(it) },
                onFailure = { Resource.Error(NetworkError.UNAUTHORIZED) }
            )

        emit(result)
    }
}