package com.example.nutritiontracker.states.requests

sealed class GetMealByIdMealRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): GetMealByIdMealRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): GetMealByIdMealRequest<T>()
    data class Failure(val error: String = "ERROR"): GetMealByIdMealRequest<Nothing>()
}
