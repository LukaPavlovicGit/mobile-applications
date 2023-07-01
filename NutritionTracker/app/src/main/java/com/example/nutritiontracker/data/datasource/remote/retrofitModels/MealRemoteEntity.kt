package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class MealRemoteEntity(
    @SerializedName("idMeal")       val remoteIdMeal: String,
    @SerializedName("strMeal")      val name: String,
    @SerializedName("strMealThumb") val imageUri: String
)
