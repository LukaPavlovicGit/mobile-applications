package com.example.nutritiontracker.states.requests

sealed class AddMealRequest{
    data class Success(val id: Long): AddMealRequest()
    object Error: AddMealRequest()
}
