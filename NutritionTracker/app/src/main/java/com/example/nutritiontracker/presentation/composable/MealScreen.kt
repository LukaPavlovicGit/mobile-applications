package com.example.nutritiontracker.presentation.composable

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.events.MealEvent
import com.example.nutritiontracker.states.data.MealDetailsState
import com.example.nutritiontracker.viewModel.MealViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MealScreen(
    mealViewModel: MealViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    BackHandler(true) {
        onBack.invoke()
    }

    val saveMeal = remember { mutableStateOf(false) }
    val expandImage = remember { mutableStateOf(false) }
    val imageUrlToExpand = remember { mutableStateOf("") }
    val mealState = mealViewModel.mealState.collectAsState()


    when{
        expandImage.value -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable(onClick = { expandImage.value = false }),
                contentAlignment = Alignment.Center
            ) {

                BackHandler(true) {
                    expandImage.value = false
                }

                AsyncImage(
                    model = imageUrlToExpand.value,
                    contentDescription = "description",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        saveMeal.value -> {
            SaveMealScreen() {
                saveMeal.value = false
            }
        }
        else -> {
            when(mealState.value){
                MealDetailsState.DataFetched -> {  }
                is MealDetailsState.Error -> {  }
                MealDetailsState.Loading -> CircularProgressIndicator()
                is MealDetailsState.Success -> {
                    val meal = (mealState.value as MealDetailsState.Success).meal
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .background(Color.LightGray)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                model = meal.imageUri,
                                contentDescription = "description",
                                modifier = Modifier
                                    .size(250.dp)
                                    .padding(10.dp)
                                    .clickable {
                                        imageUrlToExpand.value = meal.imageUri
                                        expandImage.value = true
                                    }
                            )
                            Text(
                                text = "VIDEO LINK",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .clickable {
                                        mealViewModel.onEvent(MealEvent.OpenUrl(meal.strYoutube))
                                    }
                            )
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Text(text = "NAME:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                    Text(text = meal.name, fontSize = 16.sp)
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Text(text = "CATEGORY:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                    Text(text = meal.category, fontSize = 16.sp)
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Text(text = "AREA:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                    Text(text = meal.area, fontSize = 16.sp)
                                }
                                if(meal.strTags.isNotEmpty()){
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Top,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp)
                                    ) {
                                        Text(text = "TAGS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                        Text(text = meal.strTags, fontSize = 14.sp)
                                    }
                                }
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Text(text = "INSTRUCTIONS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                    Text(text = meal.strInstructions, fontSize = 14.sp)
                                }

                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Text(text = "INGREDIENTS:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 15.dp))
                                    for (i in 1..20) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = "$i.", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(end = 10.dp))
                                            if(meal.getIngredientByNum(i).isNotEmpty()){
                                                Row(
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ){
                                                    Text(text = meal.getIngredientByNum(i) + ":", fontSize = 18.sp, modifier = Modifier.padding(end = 10.dp))
                                                    Text(text = meal.getMeasureByNum(i), fontSize = 16.sp)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)
                            ) {
                                if (!meal.saved) {
                                    Button(
                                        onClick = { saveMeal.value = true },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.fillMaxWidth(0.4f),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                                    ) {
                                        Text(text = "Save", fontSize = 20.sp)
                                    }
                                } else {
                                    Button(
                                        onClick = {

                                        },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.fillMaxWidth(0.4f)
                                    ) {
                                        Text(text = "Update", fontSize = 20.sp)
                                    }
                                    Button(
                                        onClick = { },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.fillMaxWidth(0.4f),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                    ) {
                                        Text(text = "Delete", fontSize = 20.sp)
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SaveMealScreen(
    mealViewModel: MealViewModel = viewModel(),
    onBack:() -> Unit
){

    BackHandler(true) {
        onBack.invoke()
    }

    val savingMeal = mealViewModel.savingMeal.collectAsState()
    val date = remember { mutableStateOf(LocalDate.now()) }
    val timeDialogState = rememberMaterialDialogState()
    val options = listOf(MealType.Breakfast.name, MealType.Launch.name, MealType.Dinner.name, MealType.Snack.name)
    val expanded = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(date.value)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = savingMeal.value.imageUri,
                contentDescription = "description",
                modifier = Modifier
                    .size(250.dp)
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { mealViewModel.onEvent(MealEvent.ResetImageUri) },
                            onTap = {
                                mealViewModel.onEvent(MealEvent.CameraRequest)
                            }
                        )
                    }
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = "Name:", fontSize = 22.sp, modifier = Modifier.padding(end = 15.dp))
                    Text(text = savingMeal.value.name, fontSize = 20.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Meal type:",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Text(
                        text = options[selectedIndex.value],
                        fontSize = 25.sp,
                        modifier = Modifier.clickable { expanded.value = true },
                        style = MaterialTheme.typography.body1
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .clickable { }
                ) {
                    Text(
                        text = "Date to eat:",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    TextField(
                        enabled = false,
                        value = formattedTime,
                        onValueChange = { },
                        modifier = Modifier
                            .clickable { timeDialogState.show() },
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(fontSize = 22.sp, color = Color.Black)
                    )
                }
            }

            Spacer(modifier = Modifier.size(50.dp))
            Button(
                onClick = {
                    mealViewModel.onEvent(MealEvent.SaveMeal)
                    onBack.invoke()
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "Save", fontSize = 20.sp)
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.clickable { expanded.value = true }
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false
                    }) {
                        Text(text = option)
                    }
                }
            }
            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Ok")
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    title = "Pick a date"
                ) {
                    date.value = it
                }
            }
        }
    }
}

