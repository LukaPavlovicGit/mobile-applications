package com.example.nutritiontracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealList
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar
import com.example.nutritiontracker.states.screens.ListOfMealsState
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.example.nutritiontracker.states.data.MainDataState
import com.example.nutritiontracker.states.data.NavigationData
import com.example.nutritiontracker.states.data.energyData.MenuScreenEnergyData
import com.example.nutritiontracker.states.screens.RemoteMenuScreenState
import com.example.nutritiontracker.states.data.energyData.FilterScreenEnergyData
import com.example.nutritiontracker.states.requests.AddMealState
import com.example.nutritiontracker.states.requests.DeleteMealState
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.requests.FindByIdMealState
import com.example.nutritiontracker.states.requests.UpdateMealState
import com.example.nutritiontracker.states.screens.DeleteMealScreenState
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.states.screens.SaveMealScreenState
import com.example.nutritiontracker.states.screens.SingleMealScreenState
import com.example.nutritiontracker.states.screens.UpdateMealScreenState
import com.example.nutritiontracker.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _mainDataState = MutableStateFlow(MainDataState())
    val mainDataState = _mainDataState.asStateFlow()

    private val _menuScreenEnergyData = MutableStateFlow(MenuScreenEnergyData())
    val menuScreenEnergyData = _menuScreenEnergyData.asStateFlow()

    private val _filterScreenEnergyData = MutableStateFlow(FilterScreenEnergyData())
    val filterScreenEnergyData = _filterScreenEnergyData.asStateFlow()

    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.NavigationBarScreen)
    val mainScreenState = _mainScreenState.asStateFlow()

    private val _remoteRemoteMenuScreenState = MutableStateFlow<RemoteMenuScreenState>(RemoteMenuScreenState.Default)
    val remoteMenuScreenState = _remoteRemoteMenuScreenState.asStateFlow()

    private val _filterScreenState = MutableStateFlow<FilterScreenState>(FilterScreenState.Default)
    val filterScreenState = _filterScreenState.asStateFlow()

    private val _listOfMealsState = MutableStateFlow<ListOfMealsState>(ListOfMealsState.Processing)
    val listOfMealsState = _listOfMealsState.asStateFlow()

    //**************************************************************************************

    private val _singleMealScreenState = MutableStateFlow<SingleMealScreenState>(SingleMealScreenState.Default)
    val singleMealScreenState = _singleMealScreenState.asStateFlow()

    private val _saveMealScreenState = MutableStateFlow<SaveMealScreenState>(SaveMealScreenState.Default)
    val saveMealScreenState = _saveMealScreenState.asStateFlow()

    private val _deleteMealScreenState = MutableStateFlow<DeleteMealScreenState>(DeleteMealScreenState.Processing)
    val deleteMealScreenState = _deleteMealScreenState.asStateFlow()

    private val _updateMealScreenState = MutableStateFlow<UpdateMealScreenState>(UpdateMealScreenState.Default)
    val updateMealScreenState = _updateMealScreenState.asStateFlow()

    private var _navigationData = NavigationData()
    var navigationData = _navigationData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getEnergyForMenuScreen()
            getEnergyForFilterScreen()
        }
    }

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.CategorySelection -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO){
                    mealRepository.fetchMealsByCategory(event.category.strCategory){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealList = it.data!!, savedMealList = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.MealSelection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var found = false
                    mealRepository.findByIdMeal(event.meal.idMeal){
                        when(it){
                            FindByIdMealState.NotFound -> found = false
                            is FindByIdMealState.Success -> {
                                found = true
                                _mainDataState.value = _mainDataState.value.copy(mealById = it.data)
                                _mainScreenState.value = MainScreenState.SingleMealScreen
                            }
                        }
                    }
                    if(!found){
                        mealRepository.fetchMealById(event.meal.idMeal) {
                            when (it) {
                                is RetrofitRequestState.Failure -> { }
                                is RetrofitRequestState.Success -> {
                                    _mainDataState.value = _mainDataState.value.copy(mealById = it.data)
                                    _mainScreenState.value = MainScreenState.SingleMealScreen
                                }
                                is RetrofitRequestState.NotFound -> { }
                            }
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByCategory -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByCategory(event.category){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealList = it.data, savedMealList = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByArea -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByArea(event.area){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealList = it.data, savedMealList = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByIngredient -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealList = it.data, savedMealList = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.SearchMealsByName -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealByName(event.name){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                val mealList = MealList(meals = it.data!!.meals.map { meal -> Meal(meal.idMeal, meal.strMeal, meal.strMealThumb) })
                                _mainDataState.value = _mainDataState.value.copy(mealList = mealList, savedMealList = mealList)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.SearchMealsByIngredient -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is RetrofitRequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is RetrofitRequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealList = it.data!!, savedMealList = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RetrofitRequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.SetFilterScreenState -> _filterScreenState.value = event.state
            is MainEvent.SetRemoteMenuScreenState -> _remoteRemoteMenuScreenState.value = event.state
            is MainEvent.SetMainScreenState -> _mainScreenState.value = event.state
            is MainEvent.SetSingleMealScreenState -> _singleMealScreenState.value = event.state
            is MainEvent.SetSaveMealScreenState -> _saveMealScreenState.value = event.state
            is MainEvent.SetListOfMealState -> _listOfMealsState.value = event.state
            is MainEvent.SetNavigationData -> navigationData = event.data
            is MainEvent.SearchMealsListByName -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val filtered = _mainDataState.value.savedMealList!!.meals.filter { it.strMeal.startsWith(event.name) }
                _mainDataState.value = _mainDataState.value.copy(mealList = MealList(meals = filtered))
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.SortMealsListByName -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.savedMealList!!.meals.sortedBy { it.strMeal }
                _mainDataState.value = _mainDataState.value.copy(mealList = MealList(meals = sorted))
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.MealsListAscOrder -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.mealList!!.meals.reversed()
                _mainDataState.value = _mainDataState.value.copy(mealList = MealList(meals = sorted))
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.MealsListDescOrder -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.mealList!!.meals.reversed()
                _mainDataState.value = _mainDataState.value.copy(mealList = MealList(meals = sorted))
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.SetMealPictureUri -> {
                val mealById = _mainDataState.value.mealById
                mealById!!.meals[0].strMealThumb = event.uri
                _mainDataState.value = _mainDataState.value.copy(mealById = mealById)
            }
            is MainEvent.SaveMeal -> {
                _saveMealScreenState.value = SaveMealScreenState.Processing
                viewModelScope.launch(Dispatchers.IO) {
                    val mealEntity = Mapper.mealToMealEntity(_mainDataState.value.mealById!!.meals[0])
                    mealEntity.dateToEat = event.dataToEat
                    mealEntity.mealType = event.mealType
                    mealRepository.insert(mealEntity){
                        when(it){
                            is AddMealState.Error -> _saveMealScreenState.value = SaveMealScreenState.Error(onError = {
                                                                                            _saveMealScreenState.value = SaveMealScreenState.Default
                                                                                            _singleMealScreenState.value = SingleMealScreenState.Default
                                                                                        })
                            is AddMealState.Success -> {
                                _mainDataState.value.mealById!!.meals[0].id = it.mealId
                                _mainDataState.value.mealById!!.meals[0].saved = true
                                _mainDataState.value = _mainDataState.value.copy(mealById = _mainDataState.value.mealById)
                                _saveMealScreenState.value = SaveMealScreenState.Success(onSuccess = {
                                    _saveMealScreenState.value = SaveMealScreenState.Default
                                    _singleMealScreenState.value = SingleMealScreenState.Default
                                })
                            }
                        }
                    }
                }

            }
            is MainEvent.DeleteMeal -> {
                _deleteMealScreenState.value = DeleteMealScreenState.Processing
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.delete(_mainDataState.value.mealById!!.meals[0].id){
                        when(it){
                            DeleteMealState.Error -> _deleteMealScreenState.value = DeleteMealScreenState.Error(onError = { _singleMealScreenState.value = SingleMealScreenState.Default } )
                            DeleteMealState.Success -> {
                                _deleteMealScreenState.value = DeleteMealScreenState.Success(onSuccess = {
                                    _mainScreenState.value = MainScreenState.ListOfMealsScreen
                                    _singleMealScreenState.value = SingleMealScreenState.Default
                                } )
                            }
                        }
                    }
                }
            }
            is MainEvent.UpdateMeal -> {
                _updateMealScreenState.value = UpdateMealScreenState.Processing
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.update(Mapper.mealToMealEntity(_mainDataState.value.mealById!!.meals[0])){
                        when(it){
                            UpdateMealState.Error -> _updateMealScreenState.value = UpdateMealScreenState.Error(onError = {
                                _updateMealScreenState.value = UpdateMealScreenState.Default
                                _singleMealScreenState.value = SingleMealScreenState.Default
                            })
                            UpdateMealState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealById = _mainDataState.value.mealById)
                                _updateMealScreenState.value = UpdateMealScreenState.Success(onSuccess = {

                                    _updateMealScreenState.value = UpdateMealScreenState.Default
                                    _singleMealScreenState.value = SingleMealScreenState.Default
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getEnergyForMenuScreen(){
        mealRepository.fetchAllCategories {
            when(it){
                is EnergyRequestState.Failure -> _menuScreenEnergyData.value = _menuScreenEnergyData.value.copy(allCategoriesModel = null)
                is EnergyRequestState.Success -> _menuScreenEnergyData.value = _menuScreenEnergyData.value.copy(allCategoriesModel = it.data)
            }
        }
    }

    private suspend fun getEnergyForFilterScreen(){

        mealRepository.fetchAllCategoryNames {
            when(it){
                is EnergyRequestState.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNamesModel = null)
                is EnergyRequestState.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNamesModel = it.data)
            }
        }
        mealRepository.fetchAllAreaNames {
            when(it){
                is EnergyRequestState.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNamesModel = null)
                is EnergyRequestState.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNamesModel = it.data)
            }
        }
        mealRepository.fetchAllIngredient {
            when(it){
                is EnergyRequestState.Failure -> {
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsModel = null)
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsNames = null)
                }
                is EnergyRequestState.Success -> {
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsModel = it.data)
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsNames = it.data!!.meals.map { ingredient -> ingredient.strIngredient })
                }
            }
        }
    }
}
