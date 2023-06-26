package com.example.nutritiontracker.states.screens


sealed class MainScreenState {
    object NavigationBarScreen: MainScreenState()
    object ListOfMealsScreen: MainScreenState() //, val onMealSelection: (Meal) -> Unit
    object SingleMealScreen: MainScreenState()
    object Error: MainScreenState()
}
