package com.example.nutritiontracker.states.screens

sealed class MealState {
    object Default: MealState()
    object Processing: MealState()
    data class Success(val message: String): MealState()
    data class Error(val message: String): MealState()
}
