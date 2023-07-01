package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class AreaNamesModelRemoteEntity(
    @SerializedName("meals") val names: List<AreaNameRemoteEntity>
)

data class AreaNameRemoteEntity(
    @SerializedName("strArea") val name: String
)