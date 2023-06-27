package com.example.nutritiontracker.states.requests

sealed class FindByIdMealRequest<out T>{
    data class Success<out T>(val data: T? = null): FindByIdMealRequest<T>()
    object NotFound: FindByIdMealRequest<Nothing>()
}
