package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.MainScreenState
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun ListOfMealsScreen(
    viewModel: MainViewModel = viewModel()
){
    val uiState = viewModel.mainUiState.collectAsState()

    when(uiState.value){
        is UiState.Failure -> {
            toast(LocalContext.current, (uiState.value as UiState.Failure).message)
            viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarState))
        }
        UiState.Nothing -> TODO()
        UiState.Processing -> LoadingScreen()
        is UiState.Success -> ListMeals()
    }
}

@Composable
private fun ListMeals(
    viewModel: MainViewModel = viewModel()
){
    val mainDataState = viewModel.mainDataState.collectAsState()
    LazyColumn(content = {
        items(mainDataState.value.mealsByCategoryModel!!.meals.size){ idx->
            val meal = mainDataState.value.mealsByCategoryModel!!.meals[idx]
            Box(
                modifier = Modifier.clickable { viewModel.onEvent(MainEvent.MealSelection(meal)) }
            ){

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                ){
                    LoadImageFromUrl(url = meal.strMealThumb)
                    Text(text = meal.strMeal)
                }
            }
        }
    })
}

