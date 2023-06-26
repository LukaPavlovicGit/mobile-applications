package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllAreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoriesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllCategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AllIngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealById
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealByName
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealList
import com.example.nutritiontracker.states.requests.AddMealState
import com.example.nutritiontracker.states.requests.DeleteMealState
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.requests.FindByIdMealState
import com.example.nutritiontracker.states.requests.GetAllMealsState
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.example.nutritiontracker.states.requests.UpdateMealState
import com.example.nutritiontracker.utils.Mapper

class MealRepositoryImpl (
    private val mealService: MealService,
    private val mealDao: MealDao
): MealRepository {


    override suspend fun fetchMealsByCategory(category: String, result: (RetrofitRequestState<MealList>) -> Unit) {
        val ans = mealService.fetchMealsByCategory(category)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RetrofitRequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RetrofitRequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RetrofitRequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun insert(meal: MealEntity, result: (AddMealState) -> Unit) {
        val mealId = mealDao.insert(meal)
        if(mealId > 0){
            result.invoke(AddMealState.Success(mealId = mealId))
        }
        else{
            result.invoke(AddMealState.Error)
        }

    }

    override suspend fun getAll(result: (GetAllMealsState<MealList>) -> Unit) {
        try {
            val entities = mealDao.getAll()
            result.invoke(GetAllMealsState.Success(data = MealList(meals = entities.map { Mapper.mealEntityToMeal(it) })))
        } catch (e: Exception) {
            result.invoke(GetAllMealsState.Error)
        }
    }

    override suspend fun delete(id: Long, result: (DeleteMealState) -> Unit) {
        if(mealDao.delete(id) > 0){
            result.invoke(DeleteMealState.Success)
        }
        else{
            result.invoke(DeleteMealState.Error)
        }
    }

    override suspend fun update(meal: MealEntity, result: (UpdateMealState) -> Unit) {
        if(mealDao.update(meal) > 0){
            result.invoke(UpdateMealState.Success)
        }
        else{
            result.invoke(UpdateMealState.Error)
        }
    }

    override suspend fun findByIdMeal(idMeal: String, result: (FindByIdMealState<MealById>) -> Unit) {
        val entity = mealDao.findByIdMeal(idMeal)
        if(entity != null){
            result.invoke(FindByIdMealState.Success(data = MealById(meals = listOf(Mapper.mealEntityToMealXXXXXX(entity)))))
        }
        else{
            result.invoke(FindByIdMealState.NotFound)
        }
    }

    override suspend fun fetchMealsByArea(area: String, result: (RetrofitRequestState<MealList>) -> Unit) {
        val ans = mealService.fetchMealsByArea(area)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RetrofitRequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RetrofitRequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RetrofitRequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByIngredient(ingredient: String, result: (RetrofitRequestState<MealList>) -> Unit) {
        val ans = mealService.fetchMealsByIngredient(ingredient)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RetrofitRequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RetrofitRequestState.Success(data = ans.body() , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(RetrofitRequestState.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealById(id: String, result: (RetrofitRequestState<MealById>) -> Unit) {
        val ans = mealService.fetchMealById(id)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RetrofitRequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RetrofitRequestState.Success(data = ans.body() , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(RetrofitRequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchMealByName(name: String, result: (RetrofitRequestState<MealByName>) -> Unit) {
        val ans = mealService.fetchMealByName(name)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(RetrofitRequestState.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(RetrofitRequestState.Success(data = ans.body() , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(RetrofitRequestState.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
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