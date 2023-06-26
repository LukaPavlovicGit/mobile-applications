package com.example.nutritiontracker.states.requests

sealed class UpdateMealState{
    object Success: UpdateMealState()
    object Error: UpdateMealState()
}
