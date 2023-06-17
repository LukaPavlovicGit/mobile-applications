package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadImageFromUrl
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.ListOfMealsState
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun ListMealsScreen(
    viewModel: MainViewModel = viewModel(),
    buttonText: String = "Back",
    onBack: () -> Unit,
    onErrorCallBack: () -> Unit
){
    val listOfMealsState = viewModel.listOfMealsState.collectAsState()
    when(listOfMealsState.value){
        ListOfMealsState.Processing -> LoadingScreen()
        ListOfMealsState.Success -> {
            ListMeals(viewModel = viewModel, buttonText = buttonText, onBack = onBack)
        }
        is ListOfMealsState.NotFound -> {
            toast(context = LocalContext.current, message = (listOfMealsState.value as ListOfMealsState.NotFound).message)
            (listOfMealsState.value as ListOfMealsState.NotFound).onNotFound.invoke()
        }
        is ListOfMealsState.Error -> {
            toast(context = LocalContext.current, message = (listOfMealsState.value as ListOfMealsState.Error).message)
            (listOfMealsState.value as ListOfMealsState.Error).onError.invoke()
        }
    }
}

@Composable
fun ListMeals(
    viewModel: MainViewModel = viewModel(),
    buttonText: String = "Back",
    onBack: () -> Unit
){
    val mainDataState = viewModel.mainDataState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { onBack.invoke() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                shape = RoundedCornerShape(30),
                modifier = Modifier.fillMaxWidth(0.4f).height(50.dp)
            ) {
                Text(text = buttonText, fontSize = 25.sp, color = Color.Black)
            }

            LazyColumn(content = {
                items(mainDataState.value.mealsByCriteriaModel!!.meals.size) { idx ->
                    val meal = mainDataState.value.mealsByCriteriaModel!!.meals[idx]
                    Box(
                        modifier = Modifier.clickable {

                            viewModel.onEvent(MainEvent.MealSelection(meal = meal, onBack = {
                                viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsScreen(onBack = onBack)))
                            }))

                        }
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        ) {
                            LoadImageFromUrl(url = meal.strMealThumb)
                            Text(text = meal.strMeal)
                        }
                    }
                }
            })
        }
    }
}
