package com.example.nutritiontracker.states.requests

sealed class RequestState<out T>{
    object Processing: RequestState<Nothing>()
    data class NotFound(val message: String?): RequestState<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String?): RequestState<T>()
    data class Failure(val error: String?): RequestState<Nothing>()
}
