package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
){

    val mainScreenState = viewModel.mainScreenState.collectAsState()
    when(mainScreenState.value){
        is MainScreenState.NavigationBarScreen -> {
            NavigationScreen(
                startDestination = (mainScreenState.value as MainScreenState.NavigationBarScreen).startDestination
            )
        }
        is MainScreenState.ListOfMealsScreen -> {
            ListMealsScreen(
                viewModel = viewModel,
                onBack = (mainScreenState.value as MainScreenState.ListOfMealsScreen).onBack,
                onErrorCallBack = {  }
            )
        }
        is MainScreenState.SingleMealScreen -> {
            SingleMealScreen(
                viewModel = viewModel,
                onBack = (mainScreenState.value as MainScreenState.SingleMealScreen).onBack
            )
        }
        MainScreenState.Error -> { }
    }

}
