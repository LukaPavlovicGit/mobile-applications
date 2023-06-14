package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCategoryModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import com.example.nutritiontracker.states.RequestState

interface MealRepository {


    suspend fun fetchAllCategories(result: (RequestState<AllCategoriesModel>) -> Unit)
    suspend fun fetchMealsByCategory(category: String, result: (RequestState<MealsByCategoryModel>) -> Unit)
    suspend fun fetchMealsByArea(area: String, result: (RequestState<MealsByAreaModel>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (RequestState<MealsByIngredientModel>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (RequestState<AllCategoryNamesModel>) -> Unit)
    suspend fun fetchAllAreaNames(result: (RequestState<AllAreaNamesModel>) -> Unit)
    suspend fun fetchAllIngredient(result: (RequestState<AllIngredientsModel>) -> Unit)
    suspend fun fetchMealById(id: String, result: (RequestState<MealById>) -> Unit)
}