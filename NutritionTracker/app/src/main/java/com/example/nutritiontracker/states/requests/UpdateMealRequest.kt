package com.example.nutritiontracker.states.requests

sealed class UpdateMealRequest{
    object Success: UpdateMealRequest()
    object Error: UpdateMealRequest()
}
