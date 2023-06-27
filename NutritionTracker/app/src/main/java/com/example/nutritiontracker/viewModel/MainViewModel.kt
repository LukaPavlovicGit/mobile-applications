package com.example.nutritiontracker.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.PlanedMeal
import com.example.nutritiontracker.presentation.pagination.DefaultPaginator
import com.example.nutritiontracker.states.data.CreatePlanDataState
import com.example.nutritiontracker.states.data.LocalSearchFilters
import com.example.nutritiontracker.states.screens.ListOfMealsState
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.data.MainDataState
import com.example.nutritiontracker.states.data.NavigationData
import com.example.nutritiontracker.states.data.PaginationDataState
import com.example.nutritiontracker.states.data.energyData.MenuScreenEnergyData
import com.example.nutritiontracker.states.screens.RemoteMenuScreenState
import com.example.nutritiontracker.states.data.energyData.FilterScreenEnergyData
import com.example.nutritiontracker.states.data.energyData.MealsScreenEnergyData
import com.example.nutritiontracker.states.requests.AddMealRequest
import com.example.nutritiontracker.states.requests.DeleteMealRequest
import com.example.nutritiontracker.states.requests.FetchAreaNamesRequest
import com.example.nutritiontracker.states.requests.FetchCategoriesRequest
import com.example.nutritiontracker.states.requests.FetchCategoryNamesRequest
import com.example.nutritiontracker.states.requests.FetchIngredientsModelRequest
import com.example.nutritiontracker.states.requests.FetchMealByIdMealRequest
import com.example.nutritiontracker.states.requests.FetchMealByNameRequest
import com.example.nutritiontracker.states.requests.FetchMealsByAreaRequest
import com.example.nutritiontracker.states.requests.FetchMealsByCategoryRequest
import com.example.nutritiontracker.states.requests.FetchMealsByIngredientRequest
import com.example.nutritiontracker.states.requests.GetMealByIdMealRequest
import com.example.nutritiontracker.states.requests.GetSavedMealsRequest
import com.example.nutritiontracker.states.requests.UpdateMealRequest
import com.example.nutritiontracker.states.screens.CreatePlanScreenState
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

    private val _createPlanDataState = MutableStateFlow(CreatePlanDataState())
    val createPlanDataState = _createPlanDataState.asStateFlow()

    private val _menuScreenEnergyData = MutableStateFlow(MenuScreenEnergyData())
    val menuScreenEnergyData = _menuScreenEnergyData.asStateFlow()

    private val _filterScreenEnergyData = MutableStateFlow(FilterScreenEnergyData())
    val filterScreenEnergyData = _filterScreenEnergyData.asStateFlow()

    private val _mealsScreenEnergyData = MutableStateFlow(MealsScreenEnergyData())
    val mealsScreenEnergyData = _mealsScreenEnergyData.asStateFlow()

    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.NavigationBarScreen)
    val mainScreenState = _mainScreenState.asStateFlow()

    private val _remoteRemoteMenuScreenState = MutableStateFlow<RemoteMenuScreenState>(RemoteMenuScreenState.Default)
    val remoteMenuScreenState = _remoteRemoteMenuScreenState.asStateFlow()

    private val _filterScreenState = MutableStateFlow<FilterScreenState>(FilterScreenState.Default)
    val filterScreenState = _filterScreenState.asStateFlow()

    private val _createPlanScreenState = MutableStateFlow<CreatePlanScreenState>(CreatePlanScreenState.PeriodSelection)
    val createPlanScreenState = _createPlanScreenState.asStateFlow()

    private val _listOfMealsState = MutableStateFlow<ListOfMealsState>(ListOfMealsState.Processing)
    val listOfMealsState = _listOfMealsState.asStateFlow()

    private val _singleMealScreenState = MutableStateFlow<SingleMealScreenState>(SingleMealScreenState.Default)
    val singleMealScreenState = _singleMealScreenState.asStateFlow()

    private val _saveMealScreenState = MutableStateFlow<SaveMealScreenState>(SaveMealScreenState.Default)
    val saveMealScreenState = _saveMealScreenState.asStateFlow()

    private val _deleteMealScreenState = MutableStateFlow<DeleteMealScreenState>(DeleteMealScreenState.Processing)
    val deleteMealScreenState = _deleteMealScreenState.asStateFlow()

    private val _updateMealScreenState = MutableStateFlow<UpdateMealScreenState>(UpdateMealScreenState.Default)
    val updateMealScreenState = _updateMealScreenState.asStateFlow()

    private val _localStorageFilters = MutableStateFlow(LocalSearchFilters())
    val localStorageFilters = _localStorageFilters.asStateFlow()

    private var _navigationData = NavigationData()
    var navigationData = _navigationData

    var paginationDataState = mutableStateOf(PaginationDataState())

    private val paginator = DefaultPaginator(
        initialKey = 0,
        onLoadUpdated = {
            paginationDataState.value = paginationDataState.value.copy(isLoading = it)
        },
        onRequest = {
            Result.success(listOf(Meal("","","")))
        },
        getPreviousKey = {
            paginationDataState.value.page + 1
        },
        getNextKey = {
            paginationDataState.value.page + 1
        },
        onError = {
            paginationDataState.value = paginationDataState.value.copy(error = it?.localizedMessage )
          },
        onSuccess = { items, newKey ->
            paginationDataState.value = paginationDataState.value.copy(
                items = paginationDataState.value.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

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
                    mealRepository.fetchMealsByCategory(event.category.name){
                        when(it){
                            is FetchMealsByCategoryRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealsByCategoryRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is FetchMealsByCategoryRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.GetSavedMeals -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                _mainScreenState.value = MainScreenState.ListOfMealsScreen
                viewModelScope.launch(Dispatchers.IO){
                    mealRepository.getAll {
                        when(it){
                            is GetSavedMealsRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is GetSavedMealsRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is GetSavedMealsRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.MealSelection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var found = false
                    mealRepository.findByIdMeal(event.meal.idMeal){
                        when(it){
                            is GetMealByIdMealRequest.Failure -> found = false
                            is GetMealByIdMealRequest.NotFound -> found = false
                            is GetMealByIdMealRequest.Success -> {
                                found = true
                                _mainDataState.value = _mainDataState.value.copy(mealDetails = it.data!!)
                                _mainScreenState.value = MainScreenState.SingleMealScreen
                            }

                        }
                    }
                    if(!found){
                        mealRepository.fetchMealById(event.meal.idMeal) {
                            when (it) {
                                is FetchMealByIdMealRequest.Failure -> { }
                                is FetchMealByIdMealRequest.NotFound -> { }
                                is FetchMealByIdMealRequest.Success -> {
                                    _mainDataState.value = _mainDataState.value.copy(mealDetails = it.data!!)
                                    _mainScreenState.value = MainScreenState.SingleMealScreen
                                }
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
                            is FetchMealsByCategoryRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealsByCategoryRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is FetchMealsByCategoryRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
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
                            is FetchMealsByAreaRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealsByAreaRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is FetchMealsByAreaRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
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
                            is FetchMealsByIngredientRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealsByIngredientRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is FetchMealsByIngredientRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
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
                            is FetchMealByNameRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealByNameRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealByNameRequest.Success -> {
                                val mealList = listOf(Meal(it.data!!.idMeal, it.data!!.strMeal, it.data!!.strMealThumb))
                                _mainDataState.value = _mainDataState.value.copy(meals = mealList, copyMeals = mealList)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
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
                            is FetchMealsByIngredientRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                            is FetchMealsByIngredientRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                                _listOfMealsState.value = ListOfMealsState.Success
                            }
                            is FetchMealsByIngredientRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                        }
                    }
                }
            }
            is MainEvent.SetFilterScreenState -> _filterScreenState.value = event.state
            is MainEvent.SetCreatePlanScreenState -> _createPlanScreenState.value = event.state
            is MainEvent.SetRemoteMenuScreenState -> _remoteRemoteMenuScreenState.value = event.state
            is MainEvent.SetMainScreenState -> _mainScreenState.value = event.state
            is MainEvent.SetSingleMealScreenState -> _singleMealScreenState.value = event.state
            is MainEvent.SetSaveMealScreenState -> _saveMealScreenState.value = event.state
            is MainEvent.SetListOfMealState -> _listOfMealsState.value = event.state
            is MainEvent.SetNavigationData -> navigationData = event.data
            is MainEvent.SetLocalSearchFilters -> _localStorageFilters.value = event.filters
            is MainEvent.SetCreatePlanDataState -> _createPlanDataState.value = event.data
            is MainEvent.SearchMealsListByName -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val filtered = _mainDataState.value.copyMeals.filter { it.strMeal.startsWith(event.name) }
                _mainDataState.value = _mainDataState.value.copy(meals = filtered)
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.SortMealsListByName -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.copyMeals.sortedBy { it.strMeal }
                _mainDataState.value = _mainDataState.value.copy(meals = sorted)
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.MealsListAscOrder -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.meals.reversed()
                _mainDataState.value = _mainDataState.value.copy(meals = sorted)
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.MealsListDescOrder -> {
                _listOfMealsState.value = ListOfMealsState.Processing
                val sorted = _mainDataState.value.meals.reversed()
                _mainDataState.value = _mainDataState.value.copy(meals = sorted)
                _listOfMealsState.value = ListOfMealsState.Success
            }
            is MainEvent.SetMealPictureUri -> {
                val mealDetails = _mainDataState.value.mealDetails
                mealDetails.strMealThumb = event.uri
                _mainDataState.value = _mainDataState.value.copy(mealDetails = mealDetails)
            }
            is MainEvent.SaveMeal -> {
                _saveMealScreenState.value = SaveMealScreenState.Processing
                viewModelScope.launch(Dispatchers.IO) {
                    val mealEntity = Mapper.mealToMealEntity(_mainDataState.value.mealDetails)
                    mealEntity.dateToEat = event.dataToEat
                    mealEntity.mealType = event.mealType
                    mealRepository.insert(mealEntity){
                        when(it){
                            is AddMealRequest.Error -> _saveMealScreenState.value = SaveMealScreenState.Error(onError = {
                                                                                            _saveMealScreenState.value = SaveMealScreenState.Default
                                                                                            _singleMealScreenState.value = SingleMealScreenState.Default
                                                                                        })
                            is AddMealRequest.Success -> {
                                _mainDataState.value.mealDetails.id = it.mealId
                                _mainDataState.value.mealDetails.saved = true
                                _mainDataState.value = _mainDataState.value.copy(mealDetails = _mainDataState.value.mealDetails)
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
                    mealRepository.delete(_mainDataState.value.mealDetails.id){
                        when(it){
                            DeleteMealRequest.Error -> _deleteMealScreenState.value = DeleteMealScreenState.Error(onError = { _singleMealScreenState.value = SingleMealScreenState.Default } )
                            DeleteMealRequest.Success -> {
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
                    mealRepository.update(Mapper.mealToMealEntity(_mainDataState.value.mealDetails)){
                        when(it){
                            UpdateMealRequest.Error -> _updateMealScreenState.value = UpdateMealScreenState.Error(onError = {
                                _updateMealScreenState.value = UpdateMealScreenState.Default
                                _singleMealScreenState.value = SingleMealScreenState.Default
                            })
                            UpdateMealRequest.Success -> {
                                _mainDataState.value = _mainDataState.value.copy(mealDetails = _mainDataState.value.mealDetails)
                                _updateMealScreenState.value = UpdateMealScreenState.Success(onSuccess = {

                                    _updateMealScreenState.value = UpdateMealScreenState.Default
                                    _singleMealScreenState.value = SingleMealScreenState.Default
                                })
                            }
                        }
                    }
                }
            }
            is MainEvent.FetchRemoteMealsByCategory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.fetchMealsByCategory(event.category) {
                        when (it) {
                            is FetchMealsByCategoryRequest.NotFound -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(remoteMeals = emptyList())
                            is FetchMealsByCategoryRequest.Failure -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(remoteMeals = emptyList())
                            is FetchMealsByCategoryRequest.Success -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(remoteMeals = it.data!!)
                        }
                    }
                }
            }
            is MainEvent.GetLocalMeals -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mealRepository.getAll {
                        when (it) {
                            is GetSavedMealsRequest.Failure -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(localMeals = emptyList())
                            is GetSavedMealsRequest.NotFound -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(localMeals = emptyList())
                            is GetSavedMealsRequest.Success -> _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(localMeals = it.data!!)
                        }
                    }
                }
            }
            is MainEvent.InsertToPlanFromLocal -> {
                viewModelScope.launch(Dispatchers.IO) {

                    var flag = false
                    for(meal in _createPlanDataState.value.plan){
                        if(meal.idMeal == event.mealId){
                            if(_createPlanDataState.value.from.plusDays(meal.day.toLong()).isEqual(_createPlanDataState.value.from.plusDays(_createPlanDataState.value.currDay.toLong()))){
                                flag = true
                            }
                        }
                    }
                    if(!flag){
                        mealRepository.findByIdMeal(event.mealId){
                            when(it){
                                is GetMealByIdMealRequest.Failure -> {  }
                                is GetMealByIdMealRequest.NotFound -> {  }
                                is GetMealByIdMealRequest.Success -> {
                                    val planedMeal: PlanedMeal = Mapper.mealDetailsToPlanedMeal(it.data!!)
                                    planedMeal.day = _createPlanDataState.value.currDay
                                    planedMeal.mealNum = _createPlanDataState.value.currMeal
                                    _createPlanDataState.value.plan += planedMeal
                                    _createPlanDataState.value.byDay += planedMeal
                                    _createPlanDataState.value = _createPlanDataState.value.copy(
                                        byDay = _createPlanDataState.value.byDay,
                                        currMeal = _createPlanDataState.value.currMeal + 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is MainEvent.InsertToPlanFromRemote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var flag = false
                    for(meal in _createPlanDataState.value.plan){
                        if(meal.idMeal == event.mealId ){
                            if(_createPlanDataState.value.from.plusDays(meal.day.toLong()).isEqual(_createPlanDataState.value.from.plusDays(_createPlanDataState.value.currDay.toLong()))){
                                flag = true
                            }
                        }
                    }
                    if(!flag){
                        mealRepository.fetchMealById(event.mealId){
                            when(it){
                                is FetchMealByIdMealRequest.Failure -> {  }
                                is FetchMealByIdMealRequest.NotFound -> {  }
                                is FetchMealByIdMealRequest.Success -> {
                                    val planedMeal: PlanedMeal = Mapper.mealDetailsToPlanedMeal(it.data!!)
                                    planedMeal.day = _createPlanDataState.value.currDay
                                    planedMeal.mealNum = _createPlanDataState.value.currMeal
                                    _createPlanDataState.value.plan += planedMeal
                                    _createPlanDataState.value.byDay += planedMeal
                                    _createPlanDataState.value = _createPlanDataState.value.copy(
                                        byDay = _createPlanDataState.value.byDay,
                                        currMeal = _createPlanDataState.value.currMeal + 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is MainEvent.LoadPlanedMealsByDay -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _createPlanDataState.value = _createPlanDataState.value.copy(byDay = _createPlanDataState.value.plan.filter { meal ->  meal.day == _createPlanDataState.value.currDay }.sortedBy { it.mealNum } )
                }
            }
            is MainEvent.DeletePlanedMeal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.e("TAG", "day "+ _createPlanDataState.value.currDay + ", meal " + event.mealNum.toString())
                    _createPlanDataState.value.plan = _createPlanDataState.value.plan.filterNot {
                        it.day == _createPlanDataState.value.currDay && it.mealNum == event.mealNum
                    }
                    _createPlanDataState.value = _createPlanDataState.value.copy(byDay = _createPlanDataState.value.plan.filter { meal -> meal.day == _createPlanDataState.value.currDay }.sortedBy { it.mealNum } )
                }
            }
        }
    }

    private suspend fun getEnergyForMenuScreen(){
        mealRepository.fetchAllCategories {
            when(it){
                is FetchCategoriesRequest.NotFound -> {
                    _menuScreenEnergyData.value = _menuScreenEnergyData.value.copy(categories = null)
                    _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(categories = null)
                }
                is FetchCategoriesRequest.Failure -> {
                    _menuScreenEnergyData.value = _menuScreenEnergyData.value.copy(categories = null)
                    _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(categories = null)
                }
                is FetchCategoriesRequest.Success -> {
                    _menuScreenEnergyData.value = _menuScreenEnergyData.value.copy(categories = it.data)
                    _mealsScreenEnergyData.value = _mealsScreenEnergyData.value.copy(categories = it.data)
                }
            }
        }
    }

    private suspend fun getEnergyForFilterScreen(){

        mealRepository.fetchAllCategoryNames {
            when(it){
                is FetchCategoryNamesRequest.NotFound -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNames = null)
                is FetchCategoryNamesRequest.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNames = null)
                is FetchCategoryNamesRequest.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(categoryNames = it.data)
            }
        }
        mealRepository.fetchAllAreaNames {
            when(it){
                is FetchAreaNamesRequest.NotFound -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNames = null)
                is FetchAreaNamesRequest.Failure -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNames = null)
                is FetchAreaNamesRequest.Success -> _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(areaNames = it.data)
            }
        }
        mealRepository.fetchAllIngredient {
            when(it){
                is FetchIngredientsModelRequest.NotFound -> {
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsModel = null)
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsNames = null)
                }
                is FetchIngredientsModelRequest.Failure -> {
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsModel = null)
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsNames = null)
                }
                is FetchIngredientsModelRequest.Success -> {
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsModel = it.data)
                    _filterScreenEnergyData.value = _filterScreenEnergyData.value.copy(ingredientsNames = it.data!!.meals.map { ingredient -> ingredient.name })
                }

            }
        }
    }

    private fun loadItems(nextPage: Int, pageSize: Int){

    }

    private fun f1(category: String){
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.fetchMealsByCategory(category){
                when(it){
                    is FetchMealsByCategoryRequest.Failure -> _listOfMealsState.value = ListOfMealsState.Error(onError = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                    is FetchMealsByCategoryRequest.Success -> {
                        _mainDataState.value = _mainDataState.value.copy(meals = it.data!!, copyMeals = it.data)
                        _listOfMealsState.value = ListOfMealsState.Success
                    }
                    is FetchMealsByCategoryRequest.NotFound -> _listOfMealsState.value = ListOfMealsState.NotFound(onNotFound = { _mainScreenState.value = MainScreenState.NavigationBarScreen })
                }
            }
        }
    }
}
