package com.example.nutritiontracker.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.domainModels.MealCachedDetails
import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.events.EventBusEvent
import com.example.nutritiontracker.events.MealEvent
import com.example.nutritiontracker.states.data.MealDetailsState
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
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _mealState = MutableStateFlow<MealDetailsState>(MealDetailsState.Loading)
    val mealState = _mealState.asStateFlow()

    private var _mealCachedDetails = MealCachedDetails()

    init{
        EventBus.getDefault().register(this)
    }

    fun onEvent(event: MealEvent){
        when(event){
            is MealEvent.MessageToast -> EventBus.getDefault().postSticky(EventBusEvent.MessageToast(event.message))
            MealEvent.CameraRequest -> EventBus.getDefault().postSticky(EventBusEvent.CameraRequest)
            is MealEvent.OpenUrl -> EventBus.getDefault().postSticky(EventBusEvent.OpenUrl(event.url))
            is MealEvent.SaveMeal -> EventBus.getDefault().postSticky(EventBusEvent.MessageToast(event.idMeal))
            is MealEvent.MealSelection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _mealState.value =  MealDetailsState.Loading
                    var found = false
                    mealRepository.getByIdMeal(event.idMeal){
                        when(it){
                            is Resource.Error -> _mealState.value = MealDetailsState.Error(message = "Error")
                            is Resource.Success -> {
                                found = true
                                _mealState.value = MealDetailsState.Success(meal = it.data)
                                cacheMealDetails(it.data)
                            }
                        }
                    }

                    if(found) return@launch

                    mealRepository.fetchMealById(event.idMeal){
                        when(it){
                            is Resource.Error -> MealDetailsState.Error(message = "Error")
                            is Resource.Success -> {
                                _mealState.value = MealDetailsState.Success(meal = it.data)
                                cacheMealDetails(it.data)
                            }
                        }
                    }
                }
            }
            is MealEvent.SetImageUri -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.imageUri = event.imageUrl
                _mealState.value = MealDetailsState.Success(meal = meal)
            }
            is MealEvent.SetName -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.imageUri = event.name
                _mealState.value = MealDetailsState.Success(meal = meal)
            }
            is MealEvent.SetVideoUri -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.imageUri = event.videoUrl
                _mealState.value = MealDetailsState.Success(meal = meal)
            }
            MealEvent.ResetImageUri -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.imageUri = _mealCachedDetails.imageUri
                _mealState.value = MealDetailsState.Success(meal = meal)
            }
            MealEvent.ResetName -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.name = _mealCachedDetails.name
                _mealState.value = MealDetailsState.Success(meal = meal)
            }
            MealEvent.ResetVideUri -> {
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.strYoutube = _mealCachedDetails.videoUrl
                _mealState.value = MealDetailsState.Success(meal = meal)
            }

        }
    }

    private fun cacheMealDetails(meal: MealDetails){
        _mealCachedDetails = MealCachedDetails(name = meal.name, imageUri = meal.imageUri, videoUrl = meal.strYoutube)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: EventBusEvent) {
        when (event) {
            is EventBusEvent.MealImageUrl -> {
                Log.e("TAG", event.url)
                val meal =  (_mealState.value as MealDetailsState.Success).meal
                meal.imageUri = event.url
                _mealState.value = MealDetailsState.Success(meal = meal)
                _mealState.value = (_mealState.value as MealDetailsState.Success).copy(meal = meal)
            }
            else -> { }
        }
        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event);
    }
}
