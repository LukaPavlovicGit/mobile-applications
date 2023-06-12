package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.remote.NutritionService
import com.example.nutritiontracker.data.repositories.NutritionRepository

class NutritionRepositoryImpl (
    private val nutritionService: NutritionService
): NutritionRepository {

}