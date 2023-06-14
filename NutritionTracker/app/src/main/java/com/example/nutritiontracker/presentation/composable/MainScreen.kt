package com.example.nutritiontracker.presentation.composable

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
){
    val mainScreenState = viewModel.mainScreenState.collectAsState()

    BackHandler(true) {
        viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarState))
    }

    when(mainScreenState.value){
        MainScreenState.ListOfMealsState -> ListOfMealsScreen()
        MainScreenState.SingleMealState -> SingleMealScreen()
        MainScreenState.SaveMealState -> SaveMealScreen()
        MainScreenState.NavigationBarState -> NavigationScreen()
    }
}
