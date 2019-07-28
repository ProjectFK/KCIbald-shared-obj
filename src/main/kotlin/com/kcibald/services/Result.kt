package com.kcibald.services

sealed class Result<T> {
    data class Success<T> internal constructor(val result: T) : Result<T>()
    data class Failure<T> internal constructor(val message: String) : Result<T>()
    class NotFound<T> internal constructor() : Result<T>()

    companion object {
        private val notFound = NotFound<Any>()

        fun <T> success(result: T): Result<T> = Success(result)

        @Suppress("UNCHECKED_CAST")
        fun <T> notFound(): Result<T> = notFound as Result<T>

        @Suppress("UNCHECKED_CAST")
        fun <T> failure(message: String): Result<T> = Failure<T>(message) as Result<T>
    }
}