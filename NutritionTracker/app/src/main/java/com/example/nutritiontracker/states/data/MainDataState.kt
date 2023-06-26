package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllAreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllIngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealById
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByName
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealList

data class MainDataState(
    val savedMealList: MealList? = null,
    val mealList: MealList? = null,
    val mealById: MealById? = null,
    val mealByName: MealByName? = null,
    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String> = emptyList(),
)
