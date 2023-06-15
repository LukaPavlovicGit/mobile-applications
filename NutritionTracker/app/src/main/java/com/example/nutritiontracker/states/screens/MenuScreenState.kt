package com.example.nutritiontracker.states.screens

import com.example.nutritiontracker.models.MealsByCriteriaModel

sealed class MenuScreenState {
    object Default: MenuScreenState()
    data class ListOfMeals(val data: MealsByCriteriaModel): MenuScreenState()
    object Processing: MenuScreenState()
    object Error: MenuScreenState()
}
