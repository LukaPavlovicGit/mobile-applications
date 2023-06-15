package com.example.nutritiontracker.states.requests

sealed class EnergyRequestState<out T>{
    data class Success<out T>(val data: T? = null, val message: String?): EnergyRequestState<T>()
    data class Failure(val error: String?): EnergyRequestState<Nothing>()
}
