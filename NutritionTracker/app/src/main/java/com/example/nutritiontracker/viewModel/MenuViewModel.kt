package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.domainModels.Category
import com.example.nutritiontracker.events.MenuScreenEvent
import com.example.nutritiontracker.states.data.MealsState
import com.example.nutritiontracker.states.requests.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _mealsState = MutableStateFlow<MealsState>(MealsState.Loading)
    val mealsState = _mealsState.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.fetchAllCategories {
                when(it){
                    is Resource.Error -> _categories.value = emptyList()
                    is Resource.Success -> _categories.value = it.data
                }
            }
        }
    }

    fun onEvent(event: MenuScreenEvent){
        when(event){
            is MenuScreenEvent.SearchMealsByCategory -> {
                viewModelScope.launch(Dispatchers.IO){
                    _mealsState.value = MealsState.Loading
                    mealRepository.fetchMealsByCategory(event.category){
                        when(it){
                            is Resource.Error -> _mealsState.value = MealsState.Error(message = "Error")
                            is Resource.Success -> _mealsState.value = MealsState.Success(meals = it.data)
                        }
                    }
                }
            }
            is MenuScreenEvent.SearchMealsByIngredient -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _mealsState.value = MealsState.Loading
                    mealRepository.fetchMealsByIngredient(event.ingredient){
                        when(it){
                            is Resource.Error -> _mealsState.value = MealsState.Error(message = "Error")
                            is Resource.Success -> _mealsState.value = MealsState.Success(meals = it.data)
                        }
                    }

                }
            }
            is MenuScreenEvent.SearchMealsByArea -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _mealsState.value = MealsState.Loading
                    mealRepository.fetchMealsByArea(event.area){
                        when(it){
                            is Resource.Error -> _mealsState.value = MealsState.Error(message = "Error")
                            is Resource.Success -> _mealsState.value = MealsState.Success(meals = it.data)
                        }
                    }

                }
            }
            is MenuScreenEvent.SearchMealsByTags -> {  }
            is MenuScreenEvent.SearchMealsByName -> {  }
        }
    }

    override fun onCleared() {
        super.onCleared()

    }

}