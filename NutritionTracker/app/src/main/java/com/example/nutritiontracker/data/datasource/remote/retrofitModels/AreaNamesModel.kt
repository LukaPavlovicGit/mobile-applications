package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class AreaNamesModel(
    @SerializedName("meals") val names: List<AreaName>
)

data class AreaName(
    @SerializedName("strArea") val name: String
)