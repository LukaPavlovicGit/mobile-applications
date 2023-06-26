package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllAreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoriesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllIngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealById
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByName
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealList
import com.example.nutritiontracker.states.requests.AddMealState
import com.example.nutritiontracker.states.requests.DeleteMealState
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.requests.FindByIdMealState
import com.example.nutritiontracker.states.requests.GetAllMealsState
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.example.nutritiontracker.states.requests.UpdateMealState

interface MealRepository {


    suspend fun insert(meal: MealEntity, result: (AddMealState) -> Unit)
    suspend fun getAll(result: (GetAllMealsState<MealList>) -> Unit)
    suspend fun delete(id: Long, result: (DeleteMealState) -> Unit)
    suspend fun update(meal: MealEntity, result: (UpdateMealState) -> Unit)
    suspend fun findByIdMeal(idMeal: String, result: (FindByIdMealState<MealById>) -> Unit)

    suspend fun fetchMealsByArea(area: String, result: (RetrofitRequestState<MealList>) -> Unit)
    suspend fun fetchMealsByCategory(category: String, result: (RetrofitRequestState<MealList>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (RetrofitRequestState<MealList>) -> Unit)
    suspend fun fetchMealById(id: String, result: (RetrofitRequestState<MealById>) -> Unit)
    suspend fun fetchMealByName(name: String, result: (RetrofitRequestState<MealByName>) -> Unit)

    // energy data
    suspend fun fetchAllCategories(result: (EnergyRequestState<AllCategoriesModel>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (EnergyRequestState<AllCategoryNamesModel>) -> Unit)
    suspend fun fetchAllAreaNames(result: (EnergyRequestState<AllAreaNamesModel>) -> Unit)
    suspend fun fetchAllIngredient(result: (EnergyRequestState<AllIngredientsModel>) -> Unit)
}
