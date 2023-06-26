package com.example.nutritiontracker.states.requests

sealed class GetAllMealsState<out T>{
    data class Success<out T>(val data: T? = null): GetAllMealsState<T>()
    object Error: GetAllMealsState<Nothing>()
}
