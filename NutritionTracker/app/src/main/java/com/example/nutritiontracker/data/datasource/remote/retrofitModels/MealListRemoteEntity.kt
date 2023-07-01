package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class MealListRemoteEntity(
    @SerializedName("meals") val mealRemoteEntities: List<MealRemoteEntity>
)