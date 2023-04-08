package com.example.zemoga.domain

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    class Success<T>(val data: T) : ResultState<T>()
    class Error<T>(
        val message: String?,
        val throwable: Throwable
    ) : ResultState<T>()
}
