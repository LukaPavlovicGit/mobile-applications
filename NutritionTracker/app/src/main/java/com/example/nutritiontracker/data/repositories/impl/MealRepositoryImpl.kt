package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllingredientsModel
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCategoryModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import com.example.nutritiontracker.requestState.RequestState

class MealRepositoryImpl (
    private val mealService: MealService
): MealRepository {
    override suspend fun fetchAllCategories(result: (RequestState<AllCategoriesModel>) -> Unit) {
        TODO("Not yet implemented")

    }

    override suspend fun fetchMealsByCategories(
        category: String,
        result: (RequestState<MealsByCategoryModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMealsByArea(
        area: String,
        result: (RequestState<MealsByAreaModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMealsByIngredient(
        ingredient: String,
        result: (RequestState<MealsByIngredientModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllCategoryNames(result: (RequestState<AllCategoryNamesModel>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllAreaNames(result: (RequestState<AllAreaNamesModel>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllIngredient(result: (RequestState<AllingredientsModel>) -> Unit) {
        TODO("Not yet implemented")
    }

}