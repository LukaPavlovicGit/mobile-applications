package com.example.nutritiontracker.states.requests

sealed class FindMealByIdRequest<out T>{
    data class Success<out T>(val data: T? = null): FindMealByIdRequest<T>()
    object NotFound: FindMealByIdRequest<Nothing>()
    data class Failure(val error: String = "ERROR"): FindMealByIdRequest<Nothing>()
}
