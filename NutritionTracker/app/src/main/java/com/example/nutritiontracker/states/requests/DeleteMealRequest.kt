package com.example.nutritiontracker.states.requests

sealed class DeleteMealRequest{
    object Success: DeleteMealRequest()
    object Error: DeleteMealRequest()
}
