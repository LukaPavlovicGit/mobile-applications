package com.example.nutritiontracker.states.requests

sealed class EnergyRequest<out T>{
    data class Success<out T>(val data: T? = null, val message: String?): EnergyRequest<T>()
    data class Failure(val error: String?): EnergyRequest<Nothing>()
}
