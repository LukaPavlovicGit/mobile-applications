package com.example.nutritiontracker.states.requests

sealed class Request<out T> {
    data class Success<out T>(val data: T) : Request<T>()
    data class NotFound(val message: String = "Not found") : Request<Nothing>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): Request<T>()
}