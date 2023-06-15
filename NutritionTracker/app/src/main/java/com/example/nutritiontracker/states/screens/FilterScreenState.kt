package com.example.nutritiontracker.states.screens

import com.example.nutritiontracker.models.MealsByCriteriaModel

sealed class FilterScreenState {
    object Default: FilterScreenState()
    data class ListOfMeals(val data: MealsByCriteriaModel): FilterScreenState()
    object Processing: FilterScreenState()
    object Error: FilterScreenState()
}
