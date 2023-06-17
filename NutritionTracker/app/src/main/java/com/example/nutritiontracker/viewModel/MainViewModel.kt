package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar
import com.example.nutritiontracker.states.ListOfMealsState
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.requests.RequestState
import com.example.nutritiontracker.states.data.MainDataState
import com.example.nutritiontracker.states.data.energyData.MenuScreenEnergyData
import com.example.nutritiontracker.states.screens.MenuScreenState
import com.example.nutritiontracker.states.data.energyData.FilterScreenEnergyData
import com.example.nutritiontracker.states.requests.EnergyRequestState
import com.example.nutritiontracker.states.screens.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.NavigationBarScreen(startDestination = BottomBar.Menu.route))
    val mainScreenState = _mainScreenState.asStateFlow()

    private val _menuScreenState = MutableStateFlow<MenuScreenState>(MenuScreenState.Default)
    val menuScreenState = _menuScreenState.asStateFlow()

    private val _filterScreenState = MutableStateFlow<FilterScreenState>(FilterScreenState.Default)
    val filterScreenState = _filterScreenState.asStateFlow()

    private val _listOfMealsState = MutableStateFlow<ListOfMealsState>(ListOfMealsState.Processing)
    val listOfMealsState = _listOfMealsState.asStateFlow()


    init {
        viewModelScope.launch {
            getEnergyForMenuScreen()
           getEnergyForFilterScreen()
        }
    }

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.CategorySelection -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen(onBack = event.onBack)
                viewModelScope.launch {
                    mealRepository.fetchMealsByCategory(event.category.strCategory){
                        when(it){
                            is RequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = event.onBack)
                            is RequestState.Processing -> _listOfMealsState.value = ListOfMealsState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data!!)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = event.onBack)
                        }
                    }
                }
            }
            is MainEvent.MealSelection -> {
                viewModelScope.launch {
                    mealRepository.fetchMealById(event.meal.idMeal){
                        when(it){
                            is RequestState.Failure -> { }
                            is RequestState.Processing -> { }
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealById = it.data)
                                _mainScreenState.value = MainScreenState.SingleMealScreen(onBack = event.onBack)
                            }
                            is RequestState.NotFound -> { }
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByCategory -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen(onBack = event.onBack)
                viewModelScope.launch {
                    mealRepository.fetchMealsByCategory(event.category){
                        when(it){
                            is RequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = event.onBack)
                            is RequestState.Processing -> _listOfMealsState.value = ListOfMealsState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = event.onBack)
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByArea -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen(onBack = event.onBack)
                viewModelScope.launch {
                    mealRepository.fetchMealsByArea(event.area){
                        when(it){
                            is RequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = event.onBack)
                            is RequestState.Processing -> _listOfMealsState.value = ListOfMealsState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = event.onBack)
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByIngredient -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen(onBack = event.onBack)
                viewModelScope.launch {
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is RequestState.Failure -> _listOfMealsState.value = ListOfMealsState.Error(message = "ERROR", onError = event.onBack)
                            is RequestState.Processing -> _listOfMealsState.value = ListOfMealsState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RequestState.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(message = "NOT FOUND", onNotFound = event.onBack)
                        }
                    }
                }
            }
            is MainEvent.SearchMealsByName -> {
                viewModelScope.launch {
                    mealRepository.fetchMealByName(event.name){
                        when(it){
                            is RequestState.Failure -> { }
                            RequestState.Processing -> { }
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealByName = it.data!!)

                            }
                            is RequestState.NotFound -> event.onNotFound.invoke()
                        }
                    }
                }
            }
            is MainEvent.SearchMealsByIngredient -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is RequestState.Failure -> { }
                            is RequestState.Processing -> _listOfMealsState.value = ListOfMealsState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data!!)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is RequestState.NotFound -> event.onNotFound.invoke()
                        }
                    }
                }
            }
            is MainEvent.SetFilterScreenState -> _filterScreenState.value = event.state
            is MainEvent.SetMenuScreenState -> _menuScreenState.value = event.state
            is MainEvent.SetMainScreenState -> _mainScreenState.value = event.state
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
