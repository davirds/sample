package com.davirdgs.myapplication.data.remote

import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.random.Random

interface AppService {
    suspend fun login(user: String, password: String): User
}

class MockedService : AppService {
    override suspend fun login(user: String, password: String): User {
        val random = Random.nextInt(1, 5)
        delay(random * 1000L)

        if (user != "admin" || password != "admin") {
            throw Exception("user or password invalid")
        }

        return User(
            name = "Admin",
            authToken = UUID.randomUUID().toString()
        )
    }
}

@Serializable
data class User(
    val name: String,
    val authToken: String,
)