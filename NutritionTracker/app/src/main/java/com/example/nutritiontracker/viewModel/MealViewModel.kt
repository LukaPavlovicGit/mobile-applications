package com.example.nutritiontracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.events.EventBusEvent
import com.example.nutritiontracker.events.MealEvent
import com.example.nutritiontracker.states.data.MealDetailsState
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _mealState = MutableStateFlow<MealDetailsState>(MealDetailsState.Loading)
    val mealState = _mealState.asStateFlow()

    private val _savingMeal = MutableStateFlow(MealDetails())
    val savingMeal = _savingMeal.asStateFlow()

    private lateinit var _mealCachedDetails: MealDetails

    init{
        EventBus.getDefault().register(this)
    }

    fun onEvent(event: MealEvent){
        when(event){
            is MealEvent.MessageToast -> EventBus.getDefault().postSticky(EventBusEvent.MessageToast(event.message))
            MealEvent.CameraRequest -> EventBus.getDefault().postSticky(EventBusEvent.CameraRequest)
            is MealEvent.OpenUrl -> EventBus.getDefault().postSticky(EventBusEvent.OpenUrl(event.url))
            is MealEvent.SaveMeal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // To avoid this error 'Can't toast on a thread that has not called Looper.prepare()'
                    // Toast message must be toasted from within Main thread
                    var success = false
                    mealRepository.insert(savingMeal.value){
                        when(it){
                            AddMealRequest.Error -> EventBus.getDefault().postSticky(EventBusEvent.MessageToast("Not saved"))
                            is AddMealRequest.Success -> success = true
                        }
                    }
                    if(success){
                        withContext(Dispatchers.Main){
                            EventBus.getDefault().postSticky(EventBusEvent.MessageToast("Saved"))
                            EventBus.getDefault().postSticky(EventBusEvent.MealSaved)
                        }

                        mealRepository.getByIdMeal(_mealCachedDetails.remoteIdMeal){
                            when(it){
                                is Resource.Error -> _mealState.value = MealDetailsState.Error(message = "Error")
                                is Resource.Success -> {
                                    _mealState.value = MealDetailsState.Success(meal = it.data)
                                    prepareData(it.data)
                                }
                            }
                        }
                    }
                }
            }
            is MealEvent.MealSelection -> {
                Log.e("TAG", "ULAZAK")
                //*************
                // From some reason viewModelScope doesn't work properly (it gets ignored) in the next scenario:
                // 1. meal is selected from profile screen
                // 2. menu screen is selected
                // 3. meal is selected from menu screen
                //*************
                // THIS CASE IS SOLVED BY REMOVING @Singleton annotation in ViewModelModule
                viewModelScope.launch(Dispatchers.IO) {
                    _mealState.value =  MealDetailsState.Loading
                    var found = false
                    mealRepository.getByIdMeal(event.idMeal){
                        when(it){
                            is Resource.Error -> _mealState.value = MealDetailsState.Error(message = "Error")
                            is Resource.Success -> {
                                Log.e("TAG", "Success")
                                found = true
                                _mealState.value = MealDetailsState.Success(meal = it.data)
                                prepareData(it.data)
                            }
                        }
                    }

                    if(found) {
                        Log.e("TAG", "found")
                        return@launch
                    }
                    Log.e("TAG", "not found")
                    mealRepository.fetchMealById(event.idMeal){
                        when(it){
                            is Resource.Error -> MealDetailsState.Error(message = "Error")
                            is Resource.Success -> {
                                _mealState.value = MealDetailsState.Success(meal = it.data)
                                prepareData(it.data)
                            }
                        }
                    }
                }
            }
            is MealEvent.SetImageUri -> _savingMeal.value = _savingMeal.value.copy(imageUri = event.imageUrl)
            is MealEvent.SetName ->  _savingMeal.value = _savingMeal.value.copy(name = event.name)
            is MealEvent.SetVideoUri -> _savingMeal.value = _savingMeal.value.copy(strYoutube = event.videoUrl)
            MealEvent.ResetImageUri -> _savingMeal.value = _savingMeal.value.copy(imageUri = _mealCachedDetails.imageUri)
            MealEvent.ResetName -> _savingMeal.value = _savingMeal.value.copy(name = _mealCachedDetails.name)
            MealEvent.ResetVideUri -> _savingMeal.value = _savingMeal.value.copy(strYoutube = _mealCachedDetails.strYoutube)
        }
    }

    private fun prepareData(meal: MealDetails){
        _mealCachedDetails = meal.copy()
        _savingMeal.value = meal.copy()
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("LIFE", "MealViewModel CLEARED")
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: EventBusEvent) {
        when (event) {
            is EventBusEvent.MealImageUrl -> _savingMeal.value = _savingMeal.value.copy(imageUri = event.url)
            else -> { }
        }
        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event);
    }
}
