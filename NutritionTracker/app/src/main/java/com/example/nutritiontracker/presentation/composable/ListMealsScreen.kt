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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.presentation.composable.cammon.LoadImageFromUrl
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.screens.ListOfMealsState
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun ListMealsScreen(
    viewModel: MainViewModel = viewModel()
){
    val listOfMealsState = viewModel.listOfMealsState.collectAsState()
    when(listOfMealsState.value){
        ListOfMealsState.Processing -> LoadingScreen()
        ListOfMealsState.Success -> ListMeals()
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
    viewModel: MainViewModel = viewModel()
){
    val mainDataState = viewModel.mainDataState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            SortComponent(viewModel = viewModel)
            SearchByNameComponent(viewModel = viewModel)
            Button(
                onClick = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarScreen)) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(50.dp)
            ) {
                Text(text = "Back", fontSize = 25.sp, color = Color.Black)
            }

            LazyColumn(content = {
                items(mainDataState.value.mealList!!.meals.size) { idx ->
                    val meal = mainDataState.value.mealList!!.meals[idx]
                    Box(
                        modifier = Modifier.clickable {
                            viewModel.onEvent(MainEvent.MealSelection(meal = meal))
                        }
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
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

@Composable
private fun SortComponent(
    viewModel: MainViewModel = viewModel()
){
    val options = listOf("NAME", "TODO...")
    val selectedIndex = remember { mutableStateOf(0) }
    val expanded = remember { mutableStateOf(false) }
    val orderBy = remember { mutableStateOf("ASC") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Sort by: ", fontSize = 16.sp)
            Text(
                text = options[selectedIndex.value],
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.clickable { expanded.value = true },
                style = MaterialTheme.typography.body1
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.clickable { expanded.value = true }
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false
                        when(option){
                            "NAME" -> { viewModel.onEvent(MainEvent.SortMealsListByName) }
                        }
                    }) {
                        Text(text = option)
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Order by: ", fontSize = 16.sp)
            Text(
                text = orderBy.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.clickable {
                    when(orderBy.value){
                        "ASC" -> {
                            orderBy.value = "DESC"
                            viewModel.onEvent(MainEvent.MealsListAscOrder)
                        }
                        "DESC" -> {
                            orderBy.value = "ASC"
                            viewModel.onEvent(MainEvent.MealsListDescOrder)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun SearchByNameComponent(
    viewModel: MainViewModel = viewModel()
){
    val text = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
                viewModel.onEvent(MainEvent.SearchMealsListByName(name = it)) //, onBack = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsScreen))
            },
            placeholder = { Text(text = "filter by name") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.6f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}
