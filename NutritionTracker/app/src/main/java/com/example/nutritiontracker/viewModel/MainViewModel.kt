package com.example.nutritiontracker.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealRemoteEntity
import com.example.nutritiontracker.presentation.pagination.DefaultPaginator
import com.example.nutritiontracker.states.data.ChartDataState
import com.example.nutritiontracker.states.data.CreatePlanDataState
import com.example.nutritiontracker.states.data.PaginationDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    val shouldShowNavigationBar =  MutableStateFlow(true)

    private val _createPlanDataState = MutableStateFlow(CreatePlanDataState())
    val createPlanDataState = _createPlanDataState.asStateFlow()

    private val _chartDataState = MutableStateFlow(ChartDataState())
    val chartDataState = _chartDataState.asStateFlow()

    var paginationDataState = mutableStateOf(PaginationDataState())

    private val paginator = DefaultPaginator(
        initialKey = 0,
        onLoadUpdated = {
            paginationDataState.value = paginationDataState.value.copy(isLoading = it)
        },
        onRequest = {
            Result.success(listOf(MealRemoteEntity("","","")))
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

//    fun onEvent(event: MainEvent){
//        when(event){
//            is MainEvent.GetChartData -> {
//                viewModelScope.launch(Dispatchers.IO) {
//                    mealRepository.getAllEntities {
//                        when(it){
//                            is GetSavedMealsRequest.Failure -> {  }
//                            is GetSavedMealsRequest.NotFound -> {  }
//                            is GetSavedMealsRequest.Success -> {
//                                val currentDate = LocalDate.now()
//                                val entities: List<MealDetailsLocalEntity> = it.data!!.filter { entity -> ChronoUnit.DAYS.between(entity.createdAt, currentDate) in 0..6}
//                                val map = hashMapOf<Int, Int>()
//                                for(entity in entities){
//                                    val k = ChronoUnit.DAYS.between(entity.createdAt, currentDate).toInt()
//                                    if(map[k] == null) map[k] = 1
//                                    else map[k] = map[k]!! + 1
//                                }
//                                _chartDataState.value = _chartDataState.value.copy(map = map)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
