package com.example.nutritiontracker.states.data.energyData

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllAreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllIngredientsModel

data class FilterScreenEnergyData(
    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String>? = null,
)