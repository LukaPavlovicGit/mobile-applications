package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.requests.RequestState
import com.example.nutritiontracker.states.data.MainDataState
import com.example.nutritiontracker.states.data.energyData.MenuScreenEnergyData
import com.example.nutritiontracker.states.screens.MenuScreenState
import com.example.nutritiontracker.states.data.energyData.FilterScreenEnergyData
import com.example.nutritiontracker.states.requests.EnergyRequestState
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

    // **************
    private val _menuScreenEnergyData = MutableStateFlow(MenuScreenEnergyData())
    val menuScreenEnergyData = _menuScreenEnergyData.asStateFlow()

    private val _filterScreenEnergyData = MutableStateFlow(FilterScreenEnergyData())
    val filterScreenEnergyData = _filterScreenEnergyData.asStateFlow()

    private val _menuScreenState = MutableStateFlow<MenuScreenState>(MenuScreenState.Default)
    val menuScreenState = _menuScreenState.asStateFlow()

    private val _filterScreenState = MutableStateFlow<FilterScreenState>(FilterScreenState.Default)
    val filterScreenState = _filterScreenState.asStateFlow()


    init {
        viewModelScope.launch {
            getEnergyForMenuScreen()
           getEnergyForFilterScreen()
        }
    }

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.CategorySelection -> {
                _menuScreenState.value = MenuScreenState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByCategory(event.category!!.strCategory){
                        when(it){
                            is RequestState.Failure -> _menuScreenState.value = MenuScreenState.Error
                            is RequestState.Processing -> _menuScreenState.value = MenuScreenState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data!!)
                                _menuScreenState.value = MenuScreenState.ListOfMeals(data = it.data!!)
                            }
                            is RequestState.NotFound -> _menuScreenState.value = MenuScreenState.Default
                        }
                    }
                }
            }
            is MainEvent.MealSelection -> {

                viewModelScope.launch {
                    mealRepository.fetchMealById(event.meal!!.idMeal){
                        when(it){
                            is RequestState.Failure -> { }
                            is RequestState.Processing -> { }
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealById = it.data)
                            }
                            is RequestState.NotFound -> {  }
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByCategory -> {
                _filterScreenState.value = FilterScreenState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByCategory(event.category){
                        when(it){
                            is RequestState.Failure -> _filterScreenState.value = FilterScreenState.Error
                            is RequestState.Processing -> _filterScreenState.value = FilterScreenState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _filterScreenState.value = FilterScreenState.ListOfMeals(data = it.data!!)
                            }
                            is RequestState.NotFound -> _filterScreenState.value = FilterScreenState.Default
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByArea -> {
                _filterScreenState.value = FilterScreenState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByArea(event.area){
                        when(it){
                            is RequestState.Failure -> _filterScreenState.value = FilterScreenState.Error
                            is RequestState.Processing -> _filterScreenState.value = FilterScreenState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _filterScreenState.value = FilterScreenState.ListOfMeals(data = it.data!!)
                            }
                            is RequestState.NotFound -> _filterScreenState.value = FilterScreenState.Default
                        }
                    }
                }
            }
            is MainEvent.FilterMealsByIngredient -> {
                _filterScreenState.value = FilterScreenState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is RequestState.Failure -> _filterScreenState.value = FilterScreenState.Error
                            is RequestState.Processing -> _filterScreenState.value = FilterScreenState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCriteriaModel = it.data)
                                _filterScreenState.value = FilterScreenState.ListOfMeals(data = it.data!!)
                            }
                            is RequestState.NotFound -> _filterScreenState.value = FilterScreenState.Default
                        }
                    }
                }
            }
            is MainEvent.SetFilterScreenState -> _filterScreenState.value = event.state
            is MainEvent.SetMenuScreenState -> _menuScreenState.value = event.state
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

//    private suspend fun fetchAllCategoryNames(){
//        mealRepository.fetchAllCategoryNames {
//            when(it){
//                is EnergyRequestState.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNamesModel = null)
//                is EnergyRequestState.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNamesModel = it.data)
//            }
//        }
//    }
//    private suspend fun fetchAllAreaNames(){
//        mealRepository.fetchAllAreaNames {
//            when(it){
//                is EnergyRequestState.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNamesModel = null)
//                is EnergyRequestState.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNamesModel = it.data)
//            }
//        }
//    }
//    private suspend fun fetchAllIngredients(){
//        mealRepository.fetchAllIngredient {
//            when(it){
//                is EnergyRequestState.Failure -> {
//                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsModel = null)
//                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsNames = null)
//                }
//                is EnergyRequestState.Success -> {
//                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsModel = it.data)
//                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(allIngredientsNames = it.data!!.meals.map { ingredient -> ingredient.strIngredient })
//                }
//            }
//        }
//    }

}
