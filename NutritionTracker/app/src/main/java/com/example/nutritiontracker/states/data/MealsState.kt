package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.domainModels.Meal

sealed class MealsState {
    object Loading: MealsState()
    object DataFetched: MealsState()
    data class Success(val meals: List<Meal>): MealsState()
    data class Error(val message: String): MealsState()
}