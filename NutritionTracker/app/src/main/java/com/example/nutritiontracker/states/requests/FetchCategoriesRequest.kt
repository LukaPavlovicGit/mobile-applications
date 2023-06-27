package com.example.nutritiontracker.states.requests

sealed class FetchCategoriesRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchCategoriesRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchCategoriesRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchCategoriesRequest<Nothing>()
}
