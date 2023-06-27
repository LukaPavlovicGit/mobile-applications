package com.example.nutritiontracker.states.requests

sealed class GetSavedMealsRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): GetSavedMealsRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): GetSavedMealsRequest<T>()
    data class Failure(val error: String = "ERROR"): GetSavedMealsRequest<Nothing>()
}
