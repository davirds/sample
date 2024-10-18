package com.davirdgs.myapplication.base

sealed interface Resource<out T, out E> {
    data class Success<out T, out E>(val data: T): Resource<T, E>
    data class Error<out T, out E>(val error: E): Resource<T, E>
}
