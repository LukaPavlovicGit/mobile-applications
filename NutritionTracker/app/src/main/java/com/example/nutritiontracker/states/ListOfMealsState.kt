package com.example.nutritiontracker.states

sealed class ListOfMealsState{
    object Processing: ListOfMealsState()
    object Success: ListOfMealsState()
    data class NotFound(val message: String, val onNotFound: () -> Unit): ListOfMealsState()
    data class Error(val message: String, val onError: () -> Unit): ListOfMealsState()
}
