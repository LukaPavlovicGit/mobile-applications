package com.example.nutritiontracker.states.data.energyData

import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel

data class FilterScreenEnergyData(
    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String>? = null,
)