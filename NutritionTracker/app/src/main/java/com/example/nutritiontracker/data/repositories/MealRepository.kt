package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Category
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetails
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.DeleteMealRequest
import com.example.nutritiontracker.states.requests.EnergyRequest
import com.example.nutritiontracker.states.requests.FetchAreaNamesRequest
import com.example.nutritiontracker.states.requests.FetchCategoriesRequest
import com.example.nutritiontracker.states.requests.FetchCategoryNamesRequest
import com.example.nutritiontracker.states.requests.FetchIngredientsModelRequest
import com.example.nutritiontracker.states.requests.FetchMealByIdMealRequest
import com.example.nutritiontracker.states.requests.FetchMealByNameRequest
import com.example.nutritiontracker.states.requests.FetchMealsByAreaRequest
import com.example.nutritiontracker.states.requests.FetchMealsByCategoryRequest
import com.example.nutritiontracker.states.requests.FetchMealsByIngredientRequest
import com.example.nutritiontracker.states.requests.GetMealByIdMealRequest
import com.example.nutritiontracker.states.requests.GetSavedMealsRequest
import com.example.nutritiontracker.states.requests.UpdateMealRequest

interface MealRepository {

    suspend fun insert(meal: MealEntity, result: (AddMealRequest) -> Unit)
    suspend fun getAll(result: (GetSavedMealsRequest<List<Meal>>) -> Unit)
    suspend fun delete(id: Long, result: (DeleteMealRequest) -> Unit)
    suspend fun update(meal: MealEntity, result: (UpdateMealRequest) -> Unit)
    suspend fun findByIdMeal(idMeal: String, result: (GetMealByIdMealRequest<MealDetails>) -> Unit)
    suspend fun findById(id: Long, result: (GetMealByIdMealRequest<MealEntity>) -> Unit)

    suspend fun fetchMealsByArea(area: String, result: (FetchMealsByAreaRequest<List<Meal>>) -> Unit)
    suspend fun fetchMealsByCategory(category: String, result: (FetchMealsByCategoryRequest<List<Meal>>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (FetchMealsByIngredientRequest<List<Meal>>) -> Unit)
    suspend fun fetchMealById(id: String, result: (FetchMealByIdMealRequest<MealDetails>) -> Unit)
    suspend fun fetchMealByName(name: String, result: (FetchMealByNameRequest<MealDetails>) -> Unit)

    // energy data
    suspend fun fetchAllCategories(result: (FetchCategoriesRequest<List<Category>>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (FetchCategoryNamesRequest<List<String>>) -> Unit)
    suspend fun fetchAllAreaNames(result: (FetchAreaNamesRequest<List<String>>) -> Unit)
    suspend fun fetchAllIngredient(result: (FetchIngredientsModelRequest<IngredientsModel>) -> Unit)
}
