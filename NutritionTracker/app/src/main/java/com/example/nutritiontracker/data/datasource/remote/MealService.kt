package com.example.nutritiontracker.data.datasource.remote

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AreaNamesModelRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoriesModelRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModelRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByIdMealRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByNameRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealListRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MealService {

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/categories.php")
    suspend fun fetchAllCategories(): Response<CategoriesModelRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByCategory(@Query("c") category: String): Response<MealListRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByArea(@Query("a") area: String): Response<MealListRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByIngredient(@Query("i") ingredient: String): Response<MealListRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?c=list")
    suspend fun fetchAllCategoryNames(): Response<CategoryNamesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?a=list")
    suspend fun fetchAllAreaNames(): Response<AreaNamesModelRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?i=list")
    suspend fun fetchAllIngredients(): Response<IngredientsModelRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/lookup.php")
    suspend fun fetchMealById(@Query("i") id: String): Response<MealByIdMealRemoteEntity>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/search.php")
    suspend fun fetchMealByName(@Query("s") name: String): Response<MealByNameRemoteEntity>

}