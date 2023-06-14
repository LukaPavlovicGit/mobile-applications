package com.example.nutritiontracker.presentation.composable

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.MainScreenState
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun SingleMealScreen(
    viewModel: MainViewModel = viewModel()
){

    val uiState = viewModel.mainUiState.collectAsState()

    BackHandler(true) {
        viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsState))
    }

    when(uiState.value){
        is UiState.Failure -> {
            toast(LocalContext.current, (uiState.value as UiState.Failure).message)
            viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarState))
        }
        UiState.Nothing -> TODO()
        UiState.Processing -> LoadingScreen()
        is UiState.Success -> { toast(LocalContext.current, (uiState.value as UiState.Success).message) }
    }
}
@Composable
private fun ShowMeal(
    viewModel: MainViewModel = viewModel()
){
    val mainDataState = viewModel.mainDataState.collectAsState()
}