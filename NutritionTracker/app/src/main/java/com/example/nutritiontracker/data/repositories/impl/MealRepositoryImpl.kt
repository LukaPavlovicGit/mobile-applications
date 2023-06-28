package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.AreaNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoryNamesModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModel
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Category
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetails
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.DeleteMealRequest
import com.example.nutritiontracker.states.requests.EnergyRequest
import com.example.nutritiontracker.states.requests.FetchAreaNamesRequest
import com.example.nutritiontracker.states.requests.FetchCategoriesRequest
import com.example.nutritiontracker.states.requests.FetchCategoryNamesRequest
import com.example.nutritiontracker.states.requests.FetchIngredientsModelRequest
import com.example.nutritiontracker.states.requests.FetchMealByIdMealRequest
import com.example.nutritiontracker.states.requests.FetchMealByNameRequest
import com.example.nutritiontracker.states.requests.FetchMealsByAreaRequest
import com.example.nutritiontracker.states.requests.FetchMealsByCategoryRequest
import com.example.nutritiontracker.states.requests.FetchMealsByIngredientRequest
import com.example.nutritiontracker.states.requests.GetMealByIdMealRequest
import com.example.nutritiontracker.states.requests.GetSavedMealsRequest
import com.example.nutritiontracker.states.requests.UpdateMealRequest
import com.example.nutritiontracker.utils.Mapper

class MealRepositoryImpl (
    private val mealService: MealService,
    private val mealDao: MealDao
): MealRepository {


    override suspend fun fetchMealsByCategory(category: String, result: (FetchMealsByCategoryRequest<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByCategory(category)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(FetchMealsByCategoryRequest.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(FetchMealsByCategoryRequest.Success(data = ans.body()!!.meals , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchMealsByCategoryRequest.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun insert(meal: MealEntity, result: (AddMealRequest) -> Unit) {
        val mealId = mealDao.insert(meal)
        if(mealId > 0){
            result.invoke(AddMealRequest.Success(mealId = mealId))
        }
        else{
            result.invoke(AddMealRequest.Error)
        }

    }

    override suspend fun getAll(result: (GetSavedMealsRequest<List<Meal>>) -> Unit) {
        try {
            val entities = mealDao.getAll()
            if(entities.isEmpty()){
                result.invoke(GetSavedMealsRequest.NotFound(message = "No saved meals"))
            }
            else{
                result.invoke(GetSavedMealsRequest.Success(data = entities.map { Mapper.mealEntityToMeal(it) }))
            }
        } catch (e: Exception) {
            result.invoke(GetSavedMealsRequest.Failure())
        }
    }

    override suspend fun getAllEntities(result: (GetSavedMealsRequest<List<MealEntity>>) -> Unit) {
        try {
            val entities = mealDao.getAll()
            if(entities.isEmpty()){
                result.invoke(GetSavedMealsRequest.NotFound(message = "No saved meals"))
            }
            else{
                result.invoke(GetSavedMealsRequest.Success(data = entities))
            }
        } catch (e: Exception) {
            result.invoke(GetSavedMealsRequest.Failure())
        }
    }

    override suspend fun delete(id: Long, result: (DeleteMealRequest) -> Unit) {
        if(mealDao.delete(id) > 0){
            result.invoke(DeleteMealRequest.Success)
        }
        else{
            result.invoke(DeleteMealRequest.Error)
        }
    }

    override suspend fun update(meal: MealEntity, result: (UpdateMealRequest) -> Unit) {
        if(mealDao.update(meal) > 0){
            result.invoke(UpdateMealRequest.Success)
        }
        else{
            result.invoke(UpdateMealRequest.Error)
        }
    }

    override suspend fun findByIdMeal(idMeal: String, result: (GetMealByIdMealRequest<MealDetails>) -> Unit) {
        val entity = mealDao.findByIdMeal(idMeal)
        if(entity != null){
            result.invoke(GetMealByIdMealRequest.Success(data = Mapper.mealEntityToMealDetails(entity)))
        }
        else{
            result.invoke(GetMealByIdMealRequest.NotFound())
        }
    }

    override suspend fun findById(id: Long, result: (GetMealByIdMealRequest<MealEntity>) -> Unit) {
        val entity = mealDao.findById(id)
        if(entity != null){
            result.invoke(GetMealByIdMealRequest.Success(data = entity))
        }
        else{
            result.invoke(GetMealByIdMealRequest.NotFound())
        }
    }

    override suspend fun fetchMealsByArea(area: String, result: (FetchMealsByAreaRequest<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByArea(area)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(FetchMealsByAreaRequest.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(FetchMealsByAreaRequest.Success(data = ans.body()!!.meals , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchMealsByAreaRequest.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealsByIngredient(ingredient: String, result: (FetchMealsByIngredientRequest<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByIngredient(ingredient)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(FetchMealsByIngredientRequest.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(FetchMealsByIngredientRequest.Success(data = ans.body()!!.meals , message = "Meals fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchMealsByIngredientRequest.Failure(error = "Something went wrong.. Meals are NOT fetched!"))
        }
    }

    override suspend fun fetchMealById(id: String, result: (FetchMealByIdMealRequest<MealDetails>) -> Unit) {
        val ans = mealService.fetchMealById(id)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null || ans.body()!!.meals.isEmpty()){
                result.invoke(FetchMealByIdMealRequest.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(FetchMealByIdMealRequest.Success(data = ans.body()!!.meals[0] , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchMealByIdMealRequest.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchMealByName(name: String, result: (FetchMealByNameRequest<MealDetails>) -> Unit) {
        val ans = mealService.fetchMealByName(name)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null || ans.body()!!.meals.isEmpty()){
                result.invoke(FetchMealByNameRequest.NotFound(message = "Not found..."))
            }
            else{
                result.invoke(FetchMealByNameRequest.Success(data = ans.body()!!.meals[0] , message = "Meal fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchMealByNameRequest.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }


    // energy data

    override suspend fun fetchAllCategories(result: (FetchCategoriesRequest<List<Category>>) -> Unit) {
        val ans = mealService.fetchAllCategories()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.categories == null || ans.body()!!.categories.isEmpty()){
                result.invoke(FetchCategoriesRequest.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(FetchCategoriesRequest.Success(data = ans.body()!!.categories , message = "Categories fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchCategoriesRequest.Failure(error = "Something went wrong.. Categories are NOT fetched!"))
        }
    }

    override suspend fun fetchAllCategoryNames(result: (FetchCategoryNamesRequest<List<String>>) -> Unit) {
        val ans = mealService.fetchAllCategoryNames()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.names == null || ans.body()!!.names.isEmpty()){
                result.invoke(FetchCategoryNamesRequest.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(FetchCategoryNamesRequest.Success(data = ans.body()!!.names.map { it.name } , message = "Category names fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchCategoryNamesRequest.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllAreaNames(result: (FetchAreaNamesRequest<List<String>>) -> Unit) {
        val ans = mealService.fetchAllAreaNames()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.names == null || ans.body()!!.names.isEmpty()){
                result.invoke(FetchAreaNamesRequest.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(FetchAreaNamesRequest.Success(data = ans.body()!!.names.map{ it.name } , message = "Area names fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchAreaNamesRequest.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }

    override suspend fun fetchAllIngredient(result: (FetchIngredientsModelRequest<IngredientsModel>) -> Unit) {
        val ans = mealService.fetchAllIngredients()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null){
                result.invoke(FetchIngredientsModelRequest.Failure(error = "Energy data not found!"))
            }
            else{
                result.invoke(FetchIngredientsModelRequest.Success(data = ans.body() , message = "Ingredients fetched successfully..."))
            }
        }
        else {
            result.invoke(FetchIngredientsModelRequest.Failure(error = "Something went wrong.. Meal is NOT fetched!"))
        }
    }
}