package com.example.nutritiontracker.states.requests

sealed class FetchMealByNameRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealByNameRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealByNameRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealByNameRequest<Nothing>()
}
