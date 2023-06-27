package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient")   val id: String,
    @SerializedName("strIngredient")  val description: String,
    @SerializedName("strDescription") val name: String,
    @SerializedName("strType")        val type: String
)