package com.example.nutritiontracker.states.requests

sealed class AddMealState{
    data class Success(val mealId: Long): AddMealState()
    object Error: AddMealState()
}
