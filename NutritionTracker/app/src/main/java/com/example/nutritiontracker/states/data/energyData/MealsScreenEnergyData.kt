package com.example.nutritiontracker.states.data.energyData

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Category
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal

data class MealsScreenEnergyData(
    val localMeals: List<Meal> = emptyList(),
    val remoteMeals: List<Meal> = emptyList(),
    val allMeals: List<Meal> = emptyList(),
    val categories: List<Category>? = null
)