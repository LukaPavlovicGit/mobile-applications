package com.example.nutritiontracker.states.requests

sealed class FetchMealsByIngredientRequest<out T>{
    data class NotFound(val message: String = "NOT FOUND"): FetchMealsByIngredientRequest<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String = "SUCCESS"): FetchMealsByIngredientRequest<T>()
    data class Failure(val error: String = "ERROR"): FetchMealsByIngredientRequest<Nothing>()
}
