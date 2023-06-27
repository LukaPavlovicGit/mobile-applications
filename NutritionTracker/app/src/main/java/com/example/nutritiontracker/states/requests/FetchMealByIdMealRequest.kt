package com.example.nutritiontracker.states.requests

sealed class FetchMealByIdMealRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealByIdMealRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealByIdMealRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealByIdMealRequest<Nothing>()
}
