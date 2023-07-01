package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.domainModels.MealDetails

sealed class MealDetailsState {
    object Loading: MealDetailsState()
    object DataFetched: MealDetailsState()
    data class Success(val meal: MealDetails): MealDetailsState()
    data class Error(val message: String): MealDetailsState()
}