package com.example.nutritiontracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.domainModels.Meal
import com.example.nutritiontracker.events.EventBusEvent
import com.example.nutritiontracker.events.ProfileEvent
import com.example.nutritiontracker.states.data.MealDetailsState
import com.example.nutritiontracker.states.data.MealsState
import com.example.nutritiontracker.states.requests.Request
import com.example.nutritiontracker.states.requests.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _mealsState = MutableStateFlow<MealsState>(MealsState.Loading)
    val mealsState = _mealsState.asStateFlow()

    init{
        EventBus.getDefault().register(this)
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.getAll {
                when(it){
                    is Request.Error -> _mealsState.value = MealsState.Error(message = "Error")
                    is Request.NotFound -> _mealsState.value = MealsState.Success(meals = emptyList())
                    is Request.Success -> _mealsState.value = MealsState.Success(meals = it.data)
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent){
        when(event){

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: EventBusEvent) {
        when (event) {
            is EventBusEvent.MealSaved -> {
                viewModelScope.launch(Dispatchers.IO){
                    mealRepository.getAll {
                        when(it){
                            is Request.Error -> _mealsState.value = MealsState.Error(message = "Error")
                            is Request.NotFound -> _mealsState.value = MealsState.Success(meals = emptyList())
                            is Request.Success -> _mealsState.value = MealsState.Success(meals = it.data)
                        }
                    }
                }
            }
            else -> { }
        }
        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event);
    }

}