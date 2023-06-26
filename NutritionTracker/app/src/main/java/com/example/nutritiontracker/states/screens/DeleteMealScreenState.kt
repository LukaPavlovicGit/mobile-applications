package com.example.nutritiontracker.states.screens

sealed class DeleteMealScreenState{
    object Processing: DeleteMealScreenState()
    data class Success(val onSuccess: () -> Unit): DeleteMealScreenState()
    data class Error(val onError: () -> Unit): DeleteMealScreenState()
}