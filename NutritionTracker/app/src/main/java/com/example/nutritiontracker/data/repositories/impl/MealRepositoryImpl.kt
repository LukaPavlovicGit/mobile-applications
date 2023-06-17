package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.models.AllAreaNamesModel
import com.example.nutritiontracker.models.AllCategoriesModel
import com.example.nutritiontracker.models.AllCategoryNamesModel
import com.example.nutritiontracker.models.AllIngredientsModel
import com.example.nutritiontracker.models.MealById
import com.example.nutritiontracker.models.MealByName
import com.example.nutritiontracker.models.MealsByCriteriaModel
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.requests.RequestState

class MealRepositoryImpl (
    private val mealService: MealService
): MealRepository {


    override suspend fun fetchMealsByCategory(category: String, result: (RequestState<MealsByCriteriaModel>) -> Unit) {
        val ans = mealService.fetchMealsByCategory(category)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByArea(area: String, result: (RequestState<MealsByCriteriaModel>) -> Unit) {
        val ans = mealService.fetchMealsByArea(area)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByIngredient(ingredient: String, result: (RequestState<MealsByCriteriaModel>) -> Unit) {
        val ans = mealService.fetchMealsByIngredient(ingredient)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealById(id: String, result: (RequestState<MealById>) -> Unit) {
        val ans = mealService.fetchMealById(id)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RequestState.Success(data = ans.body() , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchMealByName(name: String, result: (RequestState<MealByName>) -> Unit) {
        val ans = mealService.fetchMealByName(name)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RequestState.Success(data = ans.body() , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(RequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }


    // energy data

    override suspend fun fetchAllCategories(result: (EnergyRequestState<AllCategoriesModel>) -> Unit) {
        val ans = mealService.fetchAllCategories()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.categories == null){
                result.invoke(EnergyRequestState.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(EnergyRequestState.Success(data = ans.body() , message = "Categories fetched successfully..."))
            }
        }
        else {
            result.invoke(EnergyRequestState.Failure(error = "Something went wrong.. Categories are NOT fetched!"))
        }
    }

    override suspend fun fetchAllCategoryNames(result: (EnergyRequestState<AllCategoryNamesModel>) -> Unit) {
        val ans = mealService.fetchAllCategoryNames()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(EnergyRequestState.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(EnergyRequestState.Success(data = ans.body() , message = "Category names fetched successfully..."))
            }
        }
        else {
            result.invoke(EnergyRequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllAreaNames(result: (EnergyRequestState<AllAreaNamesModel>) -> Unit) {
        val ans = mealService.fetchAllAreaNames()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(EnergyRequestState.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(EnergyRequestState.Success(data = ans.body() , message = "Area names fetched successfully..."))
            }
        }
        else {
            result.invoke(EnergyRequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllIngredient(result: (EnergyRequestState<AllIngredientsModel>) -> Unit) {
        val ans = mealService.fetchAllIngredients()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(EnergyRequestState.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(EnergyRequestState.Success(data = ans.body() , message = "Ingredients fetched successfully..."))
            }
        }
        else {
            result.invoke(EnergyRequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }
}