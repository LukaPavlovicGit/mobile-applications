package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    onUrlClicked: (String?) -> Unit
){

    val mainScreenState = viewModel.mainScreenState.collectAsState()
    when(mainScreenState.value){
        is MainScreenState.NavigationBarScreen -> {
            val startDestination = (mainScreenState.value as MainScreenState.NavigationBarScreen).startDestination
            val menuScreenTabIdx = (mainScreenState.value as MainScreenState.NavigationBarScreen).menuScreenTabIdx
            val filterScreenTabIdx = (mainScreenState.value as MainScreenState.NavigationBarScreen).filterScreenTabIdx
            NavigationScreen(startDestination = startDestination, menuScreenTabIdx = menuScreenTabIdx, filterScreenTabIdx = filterScreenTabIdx)
        }
        is MainScreenState.ListOfMealsScreen -> {
            val onBack = (mainScreenState.value as MainScreenState.ListOfMealsScreen).onBack
            ListMealsScreen(viewModel = viewModel, onBack = onBack, onErrorCallBack = {  })
        }
        is MainScreenState.SingleMealScreen -> {
            val onBack = (mainScreenState.value as MainScreenState.SingleMealScreen).onBack
            SingleMealScreen(viewModel = viewModel, onUrlClicked = onUrlClicked, onBack = onBack)
        }
        MainScreenState.Error -> {

        }
    }

}
