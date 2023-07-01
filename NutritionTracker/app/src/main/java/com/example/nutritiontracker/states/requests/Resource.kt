package com.example.nutritiontracker.states.requests

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): Resource<T>()
}