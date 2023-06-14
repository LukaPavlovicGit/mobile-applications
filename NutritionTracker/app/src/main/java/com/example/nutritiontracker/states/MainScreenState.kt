package com.example.nutritiontracker.states

sealed class MainScreenState {
    object NavigationBarState: MainScreenState()
    object ListOfMealsState: MainScreenState()
    object SingleMealState: MainScreenState()
    object SaveMealState: MainScreenState()
}
