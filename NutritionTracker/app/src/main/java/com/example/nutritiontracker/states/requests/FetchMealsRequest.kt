package com.example.nutritiontracker.states.requests

sealed class FetchMealsRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealsRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealsRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealsRequest<Nothing>()
}
