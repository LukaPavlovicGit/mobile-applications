package com.example.nutritiontracker.states.requests

sealed class FetchMealsByAreaRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealsByAreaRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealsByAreaRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealsByAreaRequest<Nothing>()
}
