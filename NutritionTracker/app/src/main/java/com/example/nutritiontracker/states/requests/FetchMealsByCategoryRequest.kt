package com.example.nutritiontracker.states.requests

sealed class FetchMealsByCategoryRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealsByCategoryRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealsByCategoryRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealsByCategoryRequest<Nothing>()
}
