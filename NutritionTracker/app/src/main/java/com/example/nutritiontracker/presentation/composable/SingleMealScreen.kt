package com.example.nutritiontracker.presentation.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
        SingleMealScreenState.DeleteMeal -> DeleteMealScreen()
        SingleMealScreenState.Error -> {  }
        SingleMealScreenState.SaveMeal -> SaveMealScreen(openCamera = openCamera)
        SingleMealScreenState.UpdateMeal -> {  }
    }
}


@Composable
private fun DefaultSingleMealScreen(
    viewModel: MainViewModel = viewModel(),
    onUrlClicked: (String?) -> Unit
) {
    val mainDataState = viewModel.mainDataState.collectAsState()
    val meal = mainDataState.value.mealById

    Box(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = meal!!.meals[0].strMealThumb,
                contentDescription = "description",
                modifier = Modifier.size(250.dp).padding(10.dp)
            )

            Text(
                text = "VIDEO LINK",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clickable {
                        onUrlClicked.invoke(meal.meals[0].strYoutube)
                    })
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "NAME:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                    Text(text = meal.meals[0].strMeal, fontSize = 16.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "CATEGORY:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                    Text(text = meal.meals[0].strCategory, fontSize = 16.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "AREA:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                    Text(text = meal.meals[0].strArea, fontSize = 16.sp)
                }
                if(meal.meals[0].strTags != null && meal.meals[0].strTags.isNotEmpty()){
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(text = "TAGS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                        Text(text = meal.meals[0].strTags, fontSize = 14.sp)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "INSTRUCTIONS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                    Text(text = meal.meals[0].strInstructions, fontSize = 14.sp)
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "INGREDIENTS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                    for (i in 1..20) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "$i.", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 10.dp))
                            if(meal.meals[0].getIngredientByNum(i).isNotEmpty()){
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Text(text = meal.meals[0].getIngredientByNum(i) + ":", fontSize = 18.sp, modifier = Modifier.padding(end = 10.dp))
                                    Text(text = meal.meals[0].getMeasureByNum(i), fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }

            Button(
                enabled = !mainDataState.value.mealById!!.meals[0].saved,
                onClick = {
                    viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.SaveMeal))
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth(0.4f),
                colors = ButtonDefaults.buttonColors(backgroundColor = if(mainDataState.value.mealById!!.meals[0].saved) Color.Green else Color.Yellow)
            ) {
                Text(text = if(mainDataState.value.mealById!!.meals[0].saved) "Saved" else "Save meal", fontSize = 20.sp)
            }

            if(mainDataState.value.mealById!!.meals[0].saved){
                Button(
                    onClick = {
                        viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.UpdateMeal))
//                        viewModel.onEvent(MainEvent.UpdateMeal)
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                    Text(text = "Update", fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.DeleteMeal))
                        viewModel.onEvent(MainEvent.DeleteMeal)
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth(0.4f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "Delete", fontSize = 20.sp)
                }
            }

            Button(
                onClick = {
                    viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsScreen))
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "Back", fontSize = 20.sp)
            }




        }
    }


}

