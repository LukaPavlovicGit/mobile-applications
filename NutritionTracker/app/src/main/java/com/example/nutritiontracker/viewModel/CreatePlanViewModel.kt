package com.example.nutritiontracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.domainModels.Category
import com.example.nutritiontracker.domainModels.Meal
import com.example.nutritiontracker.domainModels.PlannedMeal
import com.example.nutritiontracker.events.CreatePlanEvent
import com.example.nutritiontracker.events.EventBusEvent
import com.example.nutritiontracker.states.data.CreatePlanDataState
import com.example.nutritiontracker.states.requests.Request
import com.example.nutritiontracker.states.requests.Resource
import com.example.nutritiontracker.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreatePlanViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _savedMeals = MutableStateFlow(emptyList<Meal>())
    val savedMeals = _savedMeals.asStateFlow()

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _remoteMeals = MutableStateFlow(emptyList<Meal>())
    val remoteMeals = _remoteMeals.asStateFlow()

    private val _from = MutableStateFlow(LocalDate.now())
    val from = _from.asStateFlow()

    private val _to = MutableStateFlow(LocalDate.now().plusDays(7))
    val to = _to.asStateFlow()

    private var _email = MutableStateFlow("lukapavlovic032@gmail.com")
    var email = _email.asStateFlow()

    private val _createPlanDataState = MutableStateFlow(CreatePlanDataState())
    val createPlanDataState = _createPlanDataState.asStateFlow()



    init{
        EventBus.getDefault().register(this)

        viewModelScope.launch(Dispatchers.IO){
            mealRepository.fetchAllCategories {
                when(it){
                    is Resource.Error -> _categories.value = emptyList()
                    is Resource.Success -> _categories.value = it.data
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.getAll {
                when(it){
                    is Request.Error -> _savedMeals.value = emptyList()
                    is Request.NotFound -> _savedMeals.value = emptyList()
                    is Request.Success -> _savedMeals.value = it.data
                }
            }
        }
    }

    fun onEvent(event: CreatePlanEvent){
        when(event){
            is CreatePlanEvent.SearchMealsByCategory -> {
                Log.e("TAG", event.category)
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByCategory(event.category){
                        when(it){
                            is Resource.Error -> _remoteMeals.value = emptyList()
                            is Resource.Success -> _remoteMeals.value = it.data
                        }
                    }
                }
            }
            is CreatePlanEvent.SetStartDate -> _from.value = event.from
            is CreatePlanEvent.SetEndDate -> _to.value = event.to
            is CreatePlanEvent.SetEmailReceiver -> _email.value = event.email
            is CreatePlanEvent.DeletePlannedMeal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _createPlanDataState.value.plan = _createPlanDataState.value.plan.filterNot { it.day == _createPlanDataState.value.currDay && it.mealNum == event.mealNum }
                    _createPlanDataState.value = _createPlanDataState.value.copy(byDay = _createPlanDataState.value.plan.filter { meal -> meal.day == _createPlanDataState.value.currDay }.sortedBy { it.mealNum } )
                }
            }
            is CreatePlanEvent.InsertToPlanFromLocal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var exist = false
                    for(meal in _createPlanDataState.value.plan){
                        if(meal.remoteIdMeal == event.remoteIdMeal){
                            if(from.value.plusDays(meal.day.toLong()).isEqual(from.value.plusDays(_createPlanDataState.value.currDay.toLong()))){
                                exist = true
                            }
                        }
                    }
                    if(exist) {
                        return@launch
                    }

                    mealRepository.getByIdMeal(event.remoteIdMeal){
                        when(it){
                            is Resource.Error -> {  }
                            is Resource.Success -> {
                                val plannedMeal: PlannedMeal = Mapper.mealDetailsToPlannedMeal(it.data)
                                plannedMeal.day = _createPlanDataState.value.currDay
                                plannedMeal.mealNum = _createPlanDataState.value.currMeal
                                _createPlanDataState.value.plan += plannedMeal
                                _createPlanDataState.value.byDay += plannedMeal
                                _createPlanDataState.value = _createPlanDataState.value.copy(
                                    byDay = _createPlanDataState.value.byDay,
                                    currMeal = _createPlanDataState.value.currMeal + 1
                                )
                            }
                        }
                    }

                }
            }
            is CreatePlanEvent.InsertToPlanFromRemote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var exist = false
                    for(meal in _createPlanDataState.value.plan) {
                        if (meal.remoteIdMeal == event.remoteIdMeal) {
                            if (from.value.plusDays(meal.day.toLong()).isEqual(from.value.plusDays(_createPlanDataState.value.currDay.toLong()))){
                                exist = true
                            }
                        }
                    }
                    if(exist) {
                        return@launch
                    }

                    mealRepository.fetchMealById(event.remoteIdMeal){
                        when(it){
                            is Resource.Error -> {  }
                            is Resource.Success -> {
                                val plannedMeal: PlannedMeal = Mapper.mealDetailsToPlannedMeal(it.data)
                                plannedMeal.day = _createPlanDataState.value.currDay
                                plannedMeal.mealNum = _createPlanDataState.value.currMeal
                                _createPlanDataState.value.plan += plannedMeal
                                _createPlanDataState.value.byDay += plannedMeal
                                _createPlanDataState.value = _createPlanDataState.value.copy(
                                    byDay = _createPlanDataState.value.byDay,
                                    currMeal = _createPlanDataState.value.currMeal + 1
                                )
                            }
                        }
                    }
                }
            }
            CreatePlanEvent.LoadMealsByDay -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _createPlanDataState.value = _createPlanDataState.value.copy(byDay = _createPlanDataState.value.plan.filter { meal ->  meal.day == _createPlanDataState.value.currDay }.sortedBy { it.mealNum } )
                }
            }
            CreatePlanEvent.SetNextDay -> _createPlanDataState.value = _createPlanDataState.value.copy(currDay = _createPlanDataState.value.currDay + 1)
            CreatePlanEvent.SetPreviousDay -> {
                if(_createPlanDataState.value.currDay > 0){
                    _createPlanDataState.value = _createPlanDataState.value.copy(currDay = _createPlanDataState.value.currDay - 1)
                }
            }
        }
    }

    @Subscribe(sticky = true)
    fun handleEvent(event: EventBusEvent) {
        when (event) {
            is EventBusEvent.MealSaved -> {
                viewModelScope.launch(Dispatchers.IO){
                    mealRepository.getAll {
                        when(it){
                            is Request.Error -> _savedMeals.value = emptyList()
                            is Request.NotFound -> _savedMeals.value = emptyList()
                            is Request.Success -> _savedMeals.value = it.data
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