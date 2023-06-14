package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealsByAreaModel
import com.example.nutritiontracker.models.MealsByCategoryModel
import com.example.nutritiontracker.models.MealsByIngredientModel
import com.example.nutritiontracker.states.RequestState

class MealRepositoryImpl (
    private val mealService: MealService
): MealRepository {

    override suspend fun fetchAllCategories(result: (RequestState<AllCategoriesModel>) -> Unit) {
       val ans = mealService.fetchAllCategories()
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Categories fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Categories are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByCategory(category: String, result: (RequestState<MealsByCategoryModel>) -> Unit) {
        val ans = mealService.fetchMealsByCategory(category)
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Meals fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByArea(area: String, result: (RequestState<MealsByAreaModel>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMealsByIngredient(ingredient: String, result: (RequestState<MealsByIngredientModel>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllCategoryNames(result: (RequestState<AllCategoryNamesModel>) -> Unit) {
        val ans = mealService.fetchAllCategoryNames()
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Meal fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllAreaNames(result: (RequestState<AllAreaNamesModel>) -> Unit) {
        val ans = mealService.fetchAllAreaNames()
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Meal fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllIngredient(result: (RequestState<AllIngredientsModel>) -> Unit) {
        val ans = mealService.fetchAllIngredients()
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Meal fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchMealById(id: String, result: (RequestState<MealById>) -> Unit) {
        val ans = mealService.fetchMealById(id)
        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Meal fetched successfully..."))
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

}