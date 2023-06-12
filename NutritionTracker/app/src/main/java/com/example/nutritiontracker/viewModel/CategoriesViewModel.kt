package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.CategoriesEvent
import com.example.nutritiontracker.presentation.UiState
import com.example.nutritiontracker.requestState.RequestState
import com.example.nutritiontracker.states.CategoriesDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _categoriesDataState = MutableStateFlow(CategoriesDataState())
    val categoriesDataState = _categoriesDataState.asStateFlow()

    private val _categoriesUiState = MutableStateFlow<UiState>(UiState.Nothing)
    val categoriesUiState = _categoriesUiState.asStateFlow()

    init {
        onEvent(CategoriesEvent.FetchCategories)
    }

    fun onEvent(event: CategoriesEvent){
        when(event){
            CategoriesEvent.FetchCategories -> {
                _categoriesUiState.value = UiState.Processing
                viewModelScope.launch {
                    mealRepository.fetchAllCategories {
                        when(it){
                            is RequestState.Failure ->  _categoriesUiState.value = UiState.Failure(message = it.error!!)
                            is RequestState.Processing ->  _categoriesUiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _categoriesDataState.value = _categoriesDataState.value.copy(allCategoriesModel = it.data)
                                _categoriesUiState.value = UiState.Success(message = it.message!!)
                            }
                        }
                    }
                }
            }
        }
    }
}