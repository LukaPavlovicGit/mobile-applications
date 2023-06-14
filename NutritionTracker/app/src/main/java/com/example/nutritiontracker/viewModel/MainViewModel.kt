package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.MainScreenState
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.states.RequestState
import com.example.nutritiontracker.states.MainDataState
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

    private val _mainUiState = MutableStateFlow<UiState>(UiState.Nothing)
    val mainUiState = _mainUiState.asStateFlow()

    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.NavigationBarState)
    val mainScreenState = _mainScreenState.asStateFlow()


    init {
        viewModelScope.launch {
            fetchAllCategories()
            fetchAllCategoryNames()
            fetchAllAreaNames()
            fetchAllIngredients()
        }
    }

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.CategorySelection -> {
                _mainScreenState.value = MainScreenState.ListOfMealsState
                _mainUiState.value = UiState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealsByCategory(event.category!!.strCategory){
                        when(it){
                            is RequestState.Failure -> _mainUiState.value = UiState.Failure(message = it.error!!)
                            is RequestState.Processing -> _mainUiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealsByCategoryModel = it.data)
                                _mainUiState.value = UiState.Success(message = it.message!!)
                            }
                        }
                    }
                }

            }
            is MainEvent.MealSelection -> {
                _mainScreenState.value = MainScreenState.SingleMealState
                _mainUiState.value = UiState.Processing
                viewModelScope.launch {
                    mealRepository.fetchMealById(event.meal!!.idMeal){
                        when(it){
                            is RequestState.Failure -> _mainUiState.value = UiState.Failure(message = it.error!!)
                            is RequestState.Processing -> _mainUiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealById = it.data)
                                _mainUiState.value = UiState.Success(message = it.message!!)
                            }
                        }
                    }
                }
            }
            is MainEvent.SetMainScreenState -> {
                _mainScreenState.value = event.mainScreenState
            }
        }
    }


    private suspend fun fetchAllCategories(){
        _mainUiState.value = UiState.Processing
        mealRepository.fetchAllCategories {
            when(it){
                is RequestState.Failure -> _mainUiState.value = UiState.Failure(message = it.error!!)
                is RequestState.Processing -> _mainUiState.value = UiState.Processing
                is RequestState.Success -> {
                    _mainDataState.value = _mainDataState.value.copy(allCategoriesModel = it.data)
                    _mainUiState.value = UiState.Success(message = it.message!!)
                }
            }
        }
    }
    private suspend fun fetchAllCategoryNames(){
        mealRepository.fetchAllCategoryNames {first ->
            when(first){
                is RequestState.Failure -> TODO("need not figured out")
                is RequestState.Processing -> TODO("need not figured out")
                is RequestState.Success -> {
                    _mainDataState.value = _mainDataState.value.copy(categoryNamesModel = first.data)

                }
            }
        }
    }
    private suspend fun fetchAllAreaNames(){
        mealRepository.fetchAllAreaNames {
            when(it){
                is RequestState.Failure -> TODO("need not figured out")
                is RequestState.Processing -> TODO("not figured out")
                is RequestState.Success -> {
                    _mainDataState.value = _mainDataState.value.copy(areaNamesModel = it.data)

                }
            }
        }
    }
    private suspend fun fetchAllIngredients(){
        mealRepository.fetchAllIngredient {
            when(it){
                is RequestState.Failure -> TODO("need not figured out")
                is RequestState.Processing -> TODO("need not figured out")
                is RequestState.Success -> {
                    _mainDataState.value = _mainDataState.value.copy(allIngredientsModel = it.data)
                    _mainDataState.value = _mainDataState.value.copy(allIngredientsNames = it.data!!.meals.map { ingredient -> ingredient.strIngredient })

                }
            }
        }
    }

}

/*

    val categoryNamesModel: AllCategoryNamesModel? = null,
    val areaNamesModel: AllAreaNamesModel? = null,
    val allIngredientsModel: AllIngredientsModel? = null,
    val allIngredientsNames: List<String> = emptyList(),

* */