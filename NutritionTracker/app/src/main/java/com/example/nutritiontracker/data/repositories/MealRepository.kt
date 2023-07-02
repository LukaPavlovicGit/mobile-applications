package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.data.datasource.local.entities.MealDetailsLocalEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModelRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealRemoteEntity
import com.example.nutritiontracker.domainModels.Category
import com.example.nutritiontracker.domainModels.Meal
import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.DeleteMealRequest
import com.example.nutritiontracker.states.requests.FetchAreaNamesRequest
import com.example.nutritiontracker.states.requests.FetchCategoryNamesRequest
import com.example.nutritiontracker.states.requests.FetchIngredientsModelRequest
import com.example.nutritiontracker.states.requests.GetMealByIdMealRequest
import com.example.nutritiontracker.states.requests.GetSavedMealsRequest
import com.example.nutritiontracker.states.requests.Request
import com.example.nutritiontracker.states.requests.Resource
import com.example.nutritiontracker.states.requests.UpdateMealRequest

interface MealRepository {

    suspend fun insert(meal: MealDetails, result: (AddMealRequest) -> Unit)
    suspend fun getAll(result: (Request<List<Meal>>) -> Unit)
    suspend fun getAllEntities(result: (GetSavedMealsRequest<List<MealDetailsLocalEntity>>) -> Unit)
    suspend fun delete(id: Long, result: (DeleteMealRequest) -> Unit)
    suspend fun update(meal: MealDetailsLocalEntity, result: (UpdateMealRequest) -> Unit)
    suspend fun getByIdMeal(idMeal: String, result: (Resource<MealDetails>) -> Unit)
    suspend fun getById(id: Long, result: (GetMealByIdMealRequest<MealDetailsLocalEntity>) -> Unit)

    suspend fun fetchMealsByCategory(category: String, result: (Resource<List<Meal>>) -> Unit)
    suspend fun fetchMealsByArea(area: String, result: (Resource<List<Meal>>) -> Unit)
    suspend fun fetchMealsByIngredient(ingredient: String, result: (Resource<List<Meal>>) -> Unit)
    suspend fun fetchMealById(id: String, result: (Resource<MealDetails>) -> Unit)
    suspend fun fetchMealByName(name: String, result: (Resource<MealDetails>) -> Unit)

    // energy data
    suspend fun fetchAllCategories(result: (Resource<List<Category>>) -> Unit)
    suspend fun fetchAllCategoryNames(result: (FetchCategoryNamesRequest<List<String>>) -> Unit)
    suspend fun fetchAllAreaNames(result: (FetchAreaNamesRequest<List<String>>) -> Unit)
    suspend fun fetchAllIngredient(result: (FetchIngredientsModelRequest<IngredientsModelRemoteEntity>) -> Unit)
}
