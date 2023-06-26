package com.example.nutritiontracker.states.screens

sealed class ListOfMealsState{
    object Processing: ListOfMealsState()
    object Success: ListOfMealsState()
    data class NotFound(val onNotFound: () -> Unit): ListOfMealsState()
    data class Error(val onError: () -> Unit): ListOfMealsState()
}
