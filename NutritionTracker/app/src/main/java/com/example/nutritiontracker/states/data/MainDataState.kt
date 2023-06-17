package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealByName
import com.example.nutritiontracker.models.MealsByCriteriaModel

data class MainDataState(
    val mealsByCriteriaModel: MealsByCriteriaModel? = null,
    val mealById: MealById? = null,
    val mealByName: MealByName? = null,
    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String> = emptyList(),
)
