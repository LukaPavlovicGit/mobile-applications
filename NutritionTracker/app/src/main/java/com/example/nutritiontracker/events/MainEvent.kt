package com.example.nutritiontracker.events

import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Category
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.states.data.CreatePlanDataState
import com.example.nutritiontracker.states.data.LocalSearchFilters
import com.example.nutritiontracker.states.data.NavigationData
import com.example.nutritiontracker.states.screens.CreatePlanScreenState
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.screens.ListOfMealsState
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.states.screens.RemoteMenuScreenState
import com.example.nutritiontracker.states.screens.SaveMealScreenState
import com.example.nutritiontracker.states.screens.SingleMealScreenState
import java.time.LocalDate

interface MainEvent {


    data class CategorySelection(val category: Category): MainEvent

    data class FilterMealsByCategory(val category: String): MainEvent

    data class FilterMealsByArea(val area: String): MainEvent

    data class FilterMealsByIngredient(val ingredient: String): MainEvent

    data class MealSelection(val meal: Meal): MainEvent

    data class SetListOfMealState(val state: ListOfMealsState): MainEvent

    data class SetMainScreenState(val state: MainScreenState): MainEvent

    data class SetRemoteMenuScreenState(val state: RemoteMenuScreenState): MainEvent

    data class SetFilterScreenState(val state: FilterScreenState): MainEvent

    data class SetCreatePlanScreenState(val state: CreatePlanScreenState): MainEvent

    data class SetSingleMealScreenState(val state: SingleMealScreenState): MainEvent

    data class SetSaveMealScreenState(val state: SaveMealScreenState): MainEvent

    data class SetNavigationData(val data: NavigationData): MainEvent

    data class SearchMealsByName(val name: String): MainEvent

    data class SearchMealsByIngredient(val ingredient: String): MainEvent

    data class SearchMealsListByName(val name: String): MainEvent

    data class SetMealPictureUri(val uri: String): MainEvent

    data class SetLocalSearchFilters(val filters: LocalSearchFilters): MainEvent

    data class SetCreatePlanDataState(val data: CreatePlanDataState): MainEvent

    data class SaveMeal(val dataToEat: LocalDate, val mealType: MealType): MainEvent

    object DeleteMeal: MainEvent

    data class FetchRemoteMealsByCategory(val category: String): MainEvent

    object GetLocalMeals: MainEvent

    data class InsertToPlanFromLocal(val mealId: String): MainEvent

    data class InsertToPlanFromRemote(val mealId: String): MainEvent

    object LoadPlanedMealsByDay: MainEvent

    data class DeletePlanedMeal(val mealNum: Int): MainEvent

    data class UpdateMeal(val dataToEat: LocalDate, val mealType: MealType): MainEvent

    object SortMealsListByName: MainEvent

    object MealsListAscOrder: MainEvent

    object MealsListDescOrder: MainEvent

    object GetSavedMeals: MainEvent

    object LocalSearchFilter: MainEvent
}
