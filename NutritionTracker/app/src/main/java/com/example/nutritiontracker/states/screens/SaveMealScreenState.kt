package com.example.nutritiontracker.states.screens

sealed class SaveMealScreenState{
    object Default: SaveMealScreenState()
    object Processing: SaveMealScreenState()
    data class Success(val onSuccess: () -> Unit): SaveMealScreenState()
    data class Error(val onError: () -> Unit): SaveMealScreenState()
}
