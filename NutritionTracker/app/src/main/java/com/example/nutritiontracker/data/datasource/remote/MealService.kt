package com.example.nutritiontracker.data.datasource.remote

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllingredientsModel
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCategoryModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MealService {

    @Headers("Accept: application/json")
    @GET("/categories.php")
    suspend fun getAllCategories(): Response<AllCategoriesModel>

    @Headers("Accept: application/json")
    @GET("/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealsByCategoryModel>

    @Headers("Accept: application/json")
    @GET("/filter.php")
    suspend fun getMealsByArea(@Query("a") category: String): Response<MealsByAreaModel>

    @Headers("Accept: application/json")
    @GET("/filter.php")
    suspend fun getMealsByIngredient(@Query("i") category: String): Response<MealsByIngredientModel>

    @Headers("Accept: application/json")
    @GET("/list.php?c=list")
    suspend fun getAllCategoryNames(): Response<AllCategoryNamesModel>

    @Headers("Accept: application/json")
    @GET("/list.php?a=list")
    suspend fun getAllAreaNames(): Response<AllAreaNamesModel>

    @Headers("Accept: application/json")
    @GET("/list.php?i=list")
    suspend fun getAllIngredients(): Response<AllingredientsModel>

}