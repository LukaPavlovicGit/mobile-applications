package com.example.nutritiontracker.presentation.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadImageFromUrl
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.states.screens.SingleMealScreenState
import com.example.nutritiontracker.viewModel.MainViewModel


@Composable
fun SingleMealScreen(
    viewModel: MainViewModel = viewModel(),
    onUrlClicked: (String?) -> Unit,
    openCamera: () -> Unit
){

    val singleMealScreenState = viewModel.singleMealScreenState.collectAsState()
    when(singleMealScreenState.value){
        SingleMealScreenState.Default -> DefaultSingleMealScreen(onUrlClicked = onUrlClicked)
        SingleMealScreenState.DeleteMeal -> {
            Log.e("DELSCREEN" ,"DDAA")
            DeleteMealScreen()
        }
        SingleMealScreenState.Error -> {  }
        SingleMealScreenState.SaveMeal -> SaveMealScreen(openCamera = openCamera)
        SingleMealScreenState.UpdateMeal -> {  }
    }
}


@Composable
private fun DefaultSingleMealScreen(
    viewModel: MainViewModel = viewModel(),
    onUrlClicked: (String?) -> Unit
){
    val mainDataState = viewModel.mainDataState.collectAsState()
    val meal = mainDataState.value.mealById

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ){
                LoadImageFromUrl(url = meal!!.meals[0].strMealThumb)

                Column {
                    Text(text = meal.meals[0].strMeal)
                    Text(text = meal.meals[0].strCategory)
                    Text(text = meal.meals[0].strArea)
                }

            }

            Text(text = meal!!.meals[0].strYoutube, modifier = Modifier.clickable { onUrlClicked.invoke(meal.meals[0].strYoutube) })

            if(!mainDataState.value.mealById!!.meals[0].saved){
                Button(
                    onClick = {
                        Log.e("SAVE", "DA")
                        viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.SaveMeal))
                      },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(text = "Save meal", fontSize = 20.sp)
                }
            }
            else {
                Button(
                    onClick = {
                        viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.UpdateMeal))
//                        viewModel.onEvent(MainEvent.UpdateMeal)
                    },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(text = "Update", fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.DeleteMeal))
                        viewModel.onEvent(MainEvent.DeleteMeal)
                              },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(text = "Delete", fontSize = 20.sp)
                }
            }

            Button(
                onClick = {
                    viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsScreen))
                  },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = "Back", fontSize = 20.sp)
            }
        }
    }
}
