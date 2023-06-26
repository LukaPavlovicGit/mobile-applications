package com.example.nutritiontracker.states.screens

sealed class UpdateMealScreenState{
    object Default: UpdateMealScreenState()
    object Processing: UpdateMealScreenState()
    data class Success(val onSuccess: () -> Unit): UpdateMealScreenState()
    data class Error(val onError: () -> Unit): UpdateMealScreenState()
}
