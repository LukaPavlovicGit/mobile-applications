package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllingredientsModel
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCategoryModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import com.example.nutritiontracker.requestState.RequestState

interface MealRepository {


    suspend fun fetchAllCategories(result: (RequestState<AllCategoriesModel>) -> Unit)
    suspend fun fetchMealsByCategories(category: String, result: (RequestState<MealsByCategoryModel>) -> Unit)
    suspend fun fetchMealsByArea(area: String, result: (RequestState<MealsByAreaModel>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (RequestState<MealsByIngredientModel>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (RequestState<AllCategoryNamesModel>) -> Unit)
    suspend fun fetchAllAreaNames(result: (RequestState<AllAreaNamesModel>) -> Unit)
    suspend fun fetchAllIngredient(result: (RequestState<AllingredientsModel>) -> Unit)
}