package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    onUrlClicked: (String?) -> Unit,
    openCamera: () -> Unit,
){

    val mainScreenState = viewModel.mainScreenState.collectAsState()
    when(mainScreenState.value){
        is MainScreenState.NavigationBarScreen -> NavigationScreen()
        is MainScreenState.ListOfMealsScreen -> ListMealsScreen()
        is MainScreenState.SingleMealScreen -> SingleMealScreen(onUrlClicked = onUrlClicked, openCamera = openCamera)
        MainScreenState.Error -> {

        }
    }

}
