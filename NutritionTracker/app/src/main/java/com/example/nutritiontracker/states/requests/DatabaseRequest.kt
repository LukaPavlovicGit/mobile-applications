package com.example.nutritiontracker.states.requests

open class DatabaseRequest<out T> {
    data class Success<out T>(val data: T) : DatabaseRequest<T>()
    data class Loading<out T>(val message: String = "") : DatabaseRequest<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): DatabaseRequest<T>()
}