package com.example.nutritiontracker.states.requests

sealed class RetrofitRequestState<out T>{
    data class NotFound(val message: String?): RetrofitRequestState<Nothing>()
    data class Success<out T>(val data: T? = null, val message: String?): RetrofitRequestState<T>()
    data class Failure(val error: String?): RetrofitRequestState<Nothing>()
}
