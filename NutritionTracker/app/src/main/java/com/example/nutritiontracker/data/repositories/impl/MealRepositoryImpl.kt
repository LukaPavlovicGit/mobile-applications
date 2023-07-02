package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.data.datasource.local.entities.MealDetailsLocalEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.IngredientsModelRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealRemoteEntity
import com.example.nutritiontracker.domainModels.Category
import com.example.nutritiontracker.domainModels.Meal
import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.DeleteMealRequest
import com.example.nutritiontracker.states.requests.FetchAreaNamesRequest
import com.example.nutritiontracker.states.requests.FetchCategoryNamesRequest
import com.example.nutritiontracker.states.requests.FetchIngredientsModelRequest
import com.example.nutritiontracker.states.requests.GetMealByIdMealRequest
import com.example.nutritiontracker.states.requests.GetSavedMealsRequest
import com.example.nutritiontracker.states.requests.Request
import com.example.nutritiontracker.states.requests.Resource
import com.example.nutritiontracker.states.requests.UpdateMealRequest
import com.example.nutritiontracker.utils.Mapper

class MealRepositoryImpl (
    private val mealService: MealService,
    private val mealDao: MealDao
): MealRepository {


    override suspend fun fetchMealsByCategory(category: String, result: (Resource<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByCategory(category)

        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.mealRemoteEntities == null){
                result.invoke(Resource.Success(data = emptyList()))
            }
            else{
                result.invoke(Resource.Success(data = ans.body()!!.mealRemoteEntities.map{ Mapper.mealRemoteEntityToMeal(it) }))
            }
        }
        else {
            result.invoke(Resource.Error())
        }
    }

    override suspend fun insert(meal: MealDetails, result: (AddMealRequest) -> Unit) {

        val id = mealDao.insert(Mapper.mealDetailsToMealDetailsLocalEntity(meal))
        if(id > 0){
            result.invoke(AddMealRequest.Success(id = id))
        }
        else{
            result.invoke(AddMealRequest.Error)
        }

    }

    override suspend fun getAll(result: (Request<List<Meal>>) -> Unit) {
        try {
            val entities = mealDao.getAll()
            if(entities.isEmpty()){
                result.invoke(Request.NotFound(message = "No saved meals"))
            }
            else{
                result.invoke(Request.Success(data = entities.map { Mapper.mealDetailsLocalEntityToMeal(it) }))
            }
        } catch (e: Exception) {
            result.invoke(Request.Error())
        }
    }

    override suspend fun getAllEntities(result: (GetSavedMealsRequest<List<MealDetailsLocalEntity>>) -> Unit) {
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

    override suspend fun update(meal: MealDetailsLocalEntity, result: (UpdateMealRequest) -> Unit) {
        if(mealDao.update(meal) > 0){
            result.invoke(UpdateMealRequest.Success)
        }
        else{
            result.invoke(UpdateMealRequest.Error)
        }
    }

    override suspend fun getByIdMeal(idMeal: String, result: (Resource<MealDetails>) -> Unit) {
        val entity = mealDao.findByIdMeal(idMeal)
        if(entity != null){
            result.invoke(Resource.Success(data = Mapper.mealDetailsLocalEntityToMealDetails(entity)))
        }
        else{
            result.invoke(Resource.Error())
        }
    }

    override suspend fun getById(id: Long, result: (GetMealByIdMealRequest<MealDetailsLocalEntity>) -> Unit) {
        val entity = mealDao.findById(id)
        if(entity != null){
            result.invoke(GetMealByIdMealRequest.Success(data = entity))
        }
        else{
            result.invoke(GetMealByIdMealRequest.NotFound())
        }
    }

    override suspend fun fetchMealsByArea(area: String, result: (Resource<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByArea(area)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.mealRemoteEntities == null){
                result.invoke(Resource.Success(data = emptyList()))
            }
            else{
                result.invoke(Resource.Success(data = ans.body()!!.mealRemoteEntities.map{ Mapper.mealRemoteEntityToMeal(it) }))
            }
        }
        else {
            result.invoke(Resource.Error())
        }
    }

    override suspend fun fetchMealsByIngredient(ingredient: String, result: (Resource<List<Meal>>) -> Unit) {
        val ans = mealService.fetchMealsByArea(ingredient)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.mealRemoteEntities == null){
                result.invoke(Resource.Success(data = emptyList()))
            }
            else{
                result.invoke(Resource.Success(data = ans.body()!!.mealRemoteEntities.map{ Mapper.mealRemoteEntityToMeal(it) }))
            }
        }
        else {
            result.invoke(Resource.Error())
        }
    }

    override suspend fun fetchMealById(id: String, result: (Resource<MealDetails>) -> Unit) {
        val ans = mealService.fetchMealById(id)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null || ans.body()!!.meals.isEmpty()){
                result.invoke(Resource.Success(data = MealDetails()))
            }
            else{
                result.invoke(Resource.Success(data = Mapper.mealDetailsRemoteEntityToMealDetails(ans.body()!!.meals[0])))
            }
        }
        else {
            result.invoke(Resource.Error())
        }
    }

    override suspend fun fetchMealByName(name: String, result: (Resource<MealDetails>) -> Unit) {
        val ans = mealService.fetchMealByName(name)
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.meals == null || ans.body()!!.meals.isEmpty()){
                result.invoke(Resource.Success(data = MealDetails()))
            }
            else{
                result.invoke(Resource.Success(data = Mapper.mealDetailsRemoteEntityToMealDetails(ans.body()!!.meals[0])))
            }
        }
        else {
            result.invoke(Resource.Error())
        }
    }


    // energy data

    override suspend fun fetchAllCategories(result: (Resource<List<Category>>) -> Unit) {
        val ans = mealService.fetchAllCategories()
        if(ans.isSuccessful){
            if(ans.body() == null || ans.body()!!.categories == null || ans.body()!!.categories.isEmpty()){
                result.invoke(Resource.Success(data = emptyList()))
            }
            else{
                result.invoke(Resource.Success(data = ans.body()!!.categories.map { Mapper.categoryRemoteEntityToCategory(it) }))
            }
        }
        else {
            result.invoke(Resource.Error())
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

    override suspend fun fetchAllIngredient(result: (FetchIngredientsModelRequest<IngredientsModelRemoteEntity>) -> Unit) {
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