package com.example.nutritiontracker.data.datasource.remote

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCriteriaModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MealService {

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/categories.php")
    suspend fun fetchAllCategories(): Response<AllCategoriesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByCategory(@Query("c") category: String): Response<MealsByCriteriaModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByArea(@Query("a") area: String): Response<MealsByCriteriaModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/filter.php")
    suspend fun fetchMealsByIngredient(@Query("i") ingredient: String): Response<MealsByCriteriaModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?c=list")
    suspend fun fetchAllCategoryNames(): Response<AllCategoryNamesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?a=list")
    suspend fun fetchAllAreaNames(): Response<AllAreaNamesModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/list.php?i=list")
    suspend fun fetchAllIngredients(): Response<AllIngredientsModel>

    @Headers("Accept: application/json")
    @GET("/api/json/v1/1/lookup.php")
    suspend fun fetchMealById(@Query("i") id: String): Response<MealById>

}