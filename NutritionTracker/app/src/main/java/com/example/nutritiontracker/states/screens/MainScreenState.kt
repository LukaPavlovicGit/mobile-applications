package com.example.nutritiontracker.states.screens

sealed class MainScreenState {
    data class NavigationBarScreen(val startDestination: String): MainScreenState()
    data class ListOfMealsScreen(val onBack: () -> Unit): MainScreenState()
    data class SingleMealScreen(val onBack: () -> Unit): MainScreenState()
    object Error: MainScreenState()
}
