package com.example.nutritiontracker.states.requests

sealed class AddMealRequest{
    data class Success(val mealId: Long): AddMealRequest()
    object Error: AddMealRequest()
}
