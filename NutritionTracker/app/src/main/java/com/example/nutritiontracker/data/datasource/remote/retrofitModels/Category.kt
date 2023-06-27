package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")             val id: String,
    @SerializedName("strCategory")            val name: String,
    @SerializedName("strCategoryDescription") val des: String,
    @SerializedName("strCategoryThumb")       val imageUri: String
)
