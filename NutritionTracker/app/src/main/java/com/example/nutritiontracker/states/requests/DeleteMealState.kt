package com.example.nutritiontracker.states.requests

sealed class DeleteMealState{
    object Success: DeleteMealState()
    object Error: DeleteMealState()
}
