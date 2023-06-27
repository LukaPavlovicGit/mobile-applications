package com.example.nutritiontracker.data.datasource.remote

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoriesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealById
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByName
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MealService {

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/categories.php")
    suspend fun fetchAllCategories(): Response<CategoriesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByCategory(@Query("c") category: String): Response<MealList>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByArea(@Query("a") area: String): Response<MealList>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByIngredient(@Query("i") ingredient: String): Response<MealList>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?c=list")
    suspend fun fetchAllCategoryNames(): Response<CategoryNamesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?a=list")
    suspend fun fetchAllAreaNames(): Response<AreaNamesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?i=list")
    suspend fun fetchAllIngredients(): Response<IngredientsModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/lookup.php")
    suspend fun fetchMealById(@Query("i") id: String): Response<MealById>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/search.php")
    suspend fun fetchMealByName(@Query("s") name: String): Response<MealByName>

}