package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetails

data class MainDataState(
    val meals: List<Meal> = emptyList(),
    val copyMeals: List<Meal> = emptyList(),
    val mealDetails: MealDetails = MealDetails()
)
