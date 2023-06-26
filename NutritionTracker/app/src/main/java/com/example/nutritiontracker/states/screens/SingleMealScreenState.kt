package com.example.nutritiontracker.states.screens

sealed class SingleMealScreenState {
    object Default: SingleMealScreenState()
    object SaveMeal: SingleMealScreenState()
    object UpdateMeal: SingleMealScreenState()
    object DeleteMeal: SingleMealScreenState()
    object Error: SingleMealScreenState()
}
