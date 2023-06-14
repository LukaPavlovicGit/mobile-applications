package com.example.nutritiontracker.states

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealsByCategoryModel

data class MainDataState(
    val allCategoriesModel: AllCategoriesModel? = null,
    val mealsByCategoryModel: MealsByCategoryModel? = null,
    val mealById: MealById? = null,
    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String> = emptyList(),
)
