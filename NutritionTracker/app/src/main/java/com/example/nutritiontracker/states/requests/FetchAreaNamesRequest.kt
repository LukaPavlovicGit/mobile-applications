package com.example.nutritiontracker.states.requests

sealed class FetchAreaNamesRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchAreaNamesRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchAreaNamesRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchAreaNamesRequest<Nothing>()
}
