package com.example.nutritiontracker.states.requests

sealed class FindByIdMealState<out T>{
    data class Success<out T>(val data: T? = null): FindByIdMealState<T>()
    object NotFound: FindByIdMealState<Nothing>()
}
