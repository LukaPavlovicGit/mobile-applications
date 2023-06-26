package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar

data class LocalSearchFilters(
    val name: String = "",
    val mealTypeName: String = "",
    val category: String = "",
    val ingredient: String = "",
    val area: String = "",

)
