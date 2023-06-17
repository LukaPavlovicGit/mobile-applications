package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealByName
import com.example.nutritiontracker.models.MealsByCriteriaModel
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.requests.RequestState

interface MealRepository {


    suspend fun fetchMealsByArea(area: String, result: (RequestState<MealsByCriteriaModel>) -> Unit)
    suspend fun fetchMealsByCategory(category: String, result: (RequestState<MealsByCriteriaModel>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (RequestState<MealsByCriteriaModel>) -> Unit)
    suspend fun fetchMealById(id: String, result: (RequestState<MealById>) -> Unit)
    suspend fun fetchMealByName(name: String, result: (RequestState<MealByName>) -> Unit)

    // energy data
    suspend fun fetchAllCategories(result: (EnergyRequestState<AllCategoriesModel>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (EnergyRequestState<AllCategoryNamesModel>) -> Unit)
    suspend fun fetchAllAreaNames(result: (EnergyRequestState<AllAreaNamesModel>) -> Unit)
    suspend fun fetchAllIngredient(result: (EnergyRequestState<AllIngredientsModel>) -> Unit)
}
