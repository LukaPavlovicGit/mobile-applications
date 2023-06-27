package com.example.nutritiontracker.states.requests

sealed class FetchCategoryNamesRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchCategoryNamesRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchCategoryNamesRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchCategoryNamesRequest<Nothing>()
}
