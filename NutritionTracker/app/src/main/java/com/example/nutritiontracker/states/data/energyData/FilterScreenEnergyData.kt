package com.example.nutritiontracker.states.data.energyData

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModel

data class FilterScreenEnergyData(
    val categoryNames: List<String>? = null,
    val areaNames: List<String>? = null,
    val ingredientsModel: IngredientsModel? = null,
    val ingredientsNames: List<String>? = null,
)