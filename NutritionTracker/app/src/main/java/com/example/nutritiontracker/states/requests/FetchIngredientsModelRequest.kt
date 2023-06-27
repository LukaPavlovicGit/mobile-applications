package com.example.nutritiontracker.states.requests

sealed class FetchIngredientsModelRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchIngredientsModelRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchIngredientsModelRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchIngredientsModelRequest<Nothing>()
}
