package com.example.nutritiontracker.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.events.MealEvent
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.data.MealsState
import com.example.nutritiontracker.viewModel.MainViewModel
import com.example.nutritiontracker.viewModel.MealViewModel
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MealList(
    mealViewModel: MealViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    mealsState: StateFlow<MealsState>,
    onBack: () -> Unit
){

    BackHandler(true) {
        onBack.invoke()
    }

    val currMealsState = mealsState.collectAsState()
    val showMeal = remember { mutableStateOf(false) }
    val expandImage = remember { mutableStateOf(false) }
    val imageUrlToExpand = remember { mutableStateOf("") }
//    val mealState = mealViewModel.mealState.collectAsState()

    Log.e("TAG", "MealList")


    when{
        showMeal.value -> {
            mainViewModel.shouldShowNavigationBar.value = false
            MealScreen(){
                showMeal.value = false
                mainViewModel.shouldShowNavigationBar.value = true
            }
        }
        expandImage.value -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable(onClick = { expandImage.value = false }),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrlToExpand.value,
                    contentDescription = "description",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        else -> {
            when(currMealsState.value){
                MealsState.DataFetched -> {  }
                is MealsState.Error -> toast(context = LocalContext.current, message = "ERROR")
                MealsState.Loading -> CircularProgressIndicator()
                is MealsState.Success -> {
                    Box(modifier = Modifier.fillMaxSize()){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {

//                    SortComponent(viewModel = viewModel)
//                    SearchByNameComponent(viewModel = viewModel)
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 30.dp),
                                content = {
                                    val meals = (mealsState.value as MealsState.Success).meals
                                    items(meals.size) { idx ->
                                        val meal = meals[idx]
                                        Box {
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 10.dp,
                                                        top = 3.dp,
                                                        end = 10.dp,
                                                        bottom = 3.dp
                                                    )
                                            ) {
                                                AsyncImage(
                                                    model =  meal.imageUri,
                                                    contentDescription = "description",
                                                    modifier = Modifier
                                                        .size(100.dp)
                                                        .padding(end = 15.dp)
                                                        .clickable {
                                                            imageUrlToExpand.value = meal.imageUri
                                                            expandImage.value = true
                                                        }
                                                )
                                                Text(
                                                    text = meal.name,
                                                    fontSize = 15.sp,
                                                    modifier = Modifier.clickable {
                                                        mealViewModel.onEvent(MealEvent.MealSelection(meal.remoteIdMeal))
                                                        showMeal.value = true
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                            Button(
                                onClick = { onBack.invoke() },
                                shape = RoundedCornerShape(15),
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(100.dp)
                            ) {
                                Text(text = "Back")
                            }
                        }

                    }
                }
            }
        }
    }
}