package com.example.nutritiontracker.states.requests

sealed class FetchIngredientsRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchIngredientsRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchIngredientsRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchIngredientsRequest<Nothing>()
}
