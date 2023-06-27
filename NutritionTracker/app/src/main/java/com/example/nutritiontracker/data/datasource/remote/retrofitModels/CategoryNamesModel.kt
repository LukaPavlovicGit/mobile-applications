package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class CategoryNamesModel(
    @SerializedName("meals" ) val names: List<CategoryName>
)

data class CategoryName(
    @SerializedName("strCategory") val name: String
)