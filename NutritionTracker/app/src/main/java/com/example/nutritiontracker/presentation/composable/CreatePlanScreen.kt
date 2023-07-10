package com.example.nutritiontracker.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.events.CreatePlanEvent
import com.example.nutritiontracker.viewModel.CreatePlanViewModel
import com.example.nutritiontracker.viewModel.MainViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit


@Composable
fun CreatePlanScreen(
    createPlanViewModel: CreatePlanViewModel = hiltViewModel(),
){

    Log.e("PlanCreation", "pick date")
    val nextScreen = remember { mutableStateOf(false) }
    val from = createPlanViewModel.from.collectAsState()
    val to = createPlanViewModel.to.collectAsState()
    val fromTimeDialogState = rememberMaterialDialogState()
    val toTimeDialogState = rememberMaterialDialogState()
    val fromFormatted by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .format(from.value)
        }
    }
    val toFormatted by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .format(to.value)
        }
    }

    when{
        nextScreen.value -> {
            MealSelectionScreen {
                nextScreen.value = false
            }
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Select the period", fontSize = 25.sp)
                    Spacer(modifier = Modifier.size(40.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "From:", fontSize = 20.sp)
                        OutlinedTextField(
                            enabled = false,
                            value = fromFormatted,
                            onValueChange = {  },
                            modifier = Modifier
                                .clickable { fromTimeDialogState.show() },
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Red, fontWeight = FontWeight.SemiBold)
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "To:", fontSize = 20.sp)
                        OutlinedTextField(
                            enabled = false,
                            value = toFormatted,
                            onValueChange = {  },
                            modifier = Modifier
                                .clickable { toTimeDialogState.show() },
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Red, fontWeight = FontWeight.SemiBold)
                        )
                    }

                    Spacer(modifier = Modifier.size(100.dp))
                    Button(
                        onClick = {
                            if(from.value.isAfter(to.value) || from.value.isEqual(to.value)){

                            }else{
                                nextScreen.value = true
                            }
                        },
                        shape = RoundedCornerShape(15),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(50.dp)
                    ) {
                        Text(text = "Next", fontSize = 20.sp)
                    }

                    MaterialDialog(
                        dialogState = fromTimeDialogState,
                        buttons = {
                            positiveButton(text = "Ok")
                            negativeButton(text = "Cancel")
                        }
                    ) {
                        datepicker (
                            title = "Pick a date"
                        ) {
                            createPlanViewModel.onEvent(CreatePlanEvent.SetStartDate(it))
                        }
                    }

                    MaterialDialog(
                        dialogState = toTimeDialogState,
                        buttons = {
                            positiveButton(text = "Ok")
                            negativeButton(text = "Cancel")
                        }
                    ) {
                        datepicker (
                            title = "Pick a date"
                        ) {
                            createPlanViewModel.onEvent(CreatePlanEvent.SetEndDate(it))
                        }
                    }

                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun MealSelectionScreen(
    createPlanViewModel: CreatePlanViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val savedMeals = createPlanViewModel.savedMeals.collectAsState()
    val categories = createPlanViewModel.categories.collectAsState()
    val remoteMeals = createPlanViewModel.remoteMeals.collectAsState()
    val mealPlanData = createPlanViewModel.createPlanDataState.collectAsState()
    val from = createPlanViewModel.from.value
    val to = createPlanViewModel.to.value
    val showRemoteMeals = remember { mutableStateOf(false) }
    val nextScreen = remember { mutableStateOf(false) }
    val dateFormatted by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd.MM")
                .format(from.plusDays(mealPlanData.value.currDay.toLong()))
        }
    }

    when{
        nextScreen.value -> {

        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        Button(
                            enabled = mealPlanData.value.currDay > 0,
                            onClick = { createPlanViewModel.onEvent(CreatePlanEvent.SetPreviousDay) },
                            shape = RoundedCornerShape(15),
                            modifier = Modifier
                                .height(40.dp)
                                .width(100.dp)
                        ) {
                            Text(text = "Previous")
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            val daysBetween = ChronoUnit.DAYS.between(from, to)
                            Text(text = "${mealPlanData.value.currDay+1}" + " out of " + daysBetween, fontSize = 20.sp)
                            Text(text = dateFormatted, fontSize = 15.sp)
                        }
                        Button(
                            onClick = {
                                if(ChronoUnit.DAYS.between(from, to) == mealPlanData.value.currDay.toLong() + 1){
                                    nextScreen.value = true
                                }else{
                                    createPlanViewModel.onEvent(CreatePlanEvent.SetNextDay)
                                    createPlanViewModel.onEvent(CreatePlanEvent.LoadMealsByDay)                        }
                            },
                            shape = RoundedCornerShape(15),
                            modifier = Modifier
                                .height(40.dp)
                                .width(100.dp)
                        ) {
                            Text(text = "Next")
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.Green),
                    ) {
                        if (mealPlanData.value.byDay.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 50.dp),
                                content = {
                                    items(mealPlanData.value.byDay.size) { idx ->
                                        val meal = mealPlanData.value.byDay[idx]
                                        Box(
                                            modifier = Modifier.clickable {
                                            }
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp)
                                            ) {
                                                Text(text = (idx + 1).toString(), fontSize = 20.sp)
                                                Text(
                                                    text = if (meal.name.length > 18) meal.name.substring(0,15) + "..." else meal.name, fontSize = 20.sp
                                                )
                                                Button(
                                                    onClick = { },
                                                    shape = RoundedCornerShape(15),
                                                ) {
                                                    Text(text = "Remove")
                                                }
                                            }
                                        }
                                    }
                                })
                        }
                        else{
                            Text(text = "NO MEALS SELECTED", fontSize = 20.sp)
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(40.dp)
                            .height(500.dp)
                    ){
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                        ){

                            Text(text = "Saved meals:", fontSize = 20.sp, modifier = Modifier.padding(bottom = 20.dp))

                            LazyColumn(
                                content = {
                                    items(savedMeals.value.size){idx ->
                                        val meal = savedMeals.value[idx]
                                        Box {
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 10.dp)
                                            ){
                                                Row(
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ){
                                                    AsyncImage(
                                                        model = meal.imageUri,
                                                        contentDescription = "description",
                                                        modifier = Modifier
                                                            .padding(end = 20.dp)
                                                            .size(60.dp)
                                                    )
                                                    Text(text = meal.name, fontSize = 15.sp)
                                                }
                                                Spacer(modifier = Modifier.weight(0.2f))
                                                Icon(
                                                    imageVector = Icons.Filled.MoreVert,
                                                    contentDescription = "Overflow Menu",
                                                    modifier = Modifier.clickable {  }
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .height(50.dp)
                    ){
                        Canvas(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)) {
                            drawLine(
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                color = Color.Black,
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp)
                            .height(500.dp)
                    ){
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            if(!showRemoteMeals.value){
                                Text(
                                    text = "Categories:",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 20.dp)
                                )
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(bottom = 30.dp),
                                    content = {
                                        items(categories.value.size) { idx ->
                                            val category = categories.value[idx]
                                            Box (
                                                modifier = Modifier.clickable {
                                                    createPlanViewModel.onEvent(CreatePlanEvent.SearchMealsByCategory(category.name))
                                                    showRemoteMeals.value = true
                                                }
                                            ){
                                                Row(
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 10.dp)
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {
                                                        AsyncImage(
                                                            model = category.imageUri,
                                                            contentDescription = "description",
                                                            modifier = Modifier.padding(end = 20.dp)
                                                        )
                                                        Text(text = category.name, fontSize = 15.sp)
                                                    }
                                                    Spacer(modifier = Modifier.weight(0.7f))
                                                    Icon(
                                                        imageVector = Icons.Filled.MoreVert,
                                                        contentDescription = "Overflow Menu",
                                                        modifier = Modifier.clickable { }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                            else {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 20.dp)
                                ){
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Overflow Menu",
                                        modifier = Modifier.size(50.dp).padding(end = 15.dp).clickable { showRemoteMeals.value = false}
                                    )
                                    Text(
                                        text = "Meals:",
                                        fontSize = 20.sp,
                                    )
                                }
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(bottom = 30.dp),
                                    content = {
                                        items(remoteMeals.value.size){idx ->
                                            val meal = remoteMeals.value[idx]
                                            Box(
                                                modifier = Modifier.clickable {  }
                                            ){
                                                Row(
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 10.dp)
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {
                                                        AsyncImage(
                                                            model = meal.imageUri,
                                                            contentDescription = "description",
                                                            modifier = Modifier.padding(end = 20.dp).size(60.dp)
                                                        )
                                                        Text(text = if(meal.name.length > 20) meal.name.substring(0,20) + "..." else meal.name, maxLines = 1, fontSize = 15.sp)
                                                    }
                                                    Spacer(modifier = Modifier.weight(0.7f))
                                                    Icon(
                                                        imageVector = Icons.Filled.Info,
                                                        contentDescription = "Overflow Menu",
                                                        modifier = Modifier.clickable { showRemoteMeals.value = false }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmailScreen(
    createPlanViewModel: CreatePlanViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val email = createPlanViewModel.email.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email.value,
                onValueChange = {

                },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Email Address") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp)
            ) {
                Text(text = "Send")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(15),
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp)
            ) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.padding(50.dp))
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(15),
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp)
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

//@Composable
//private fun MealSelectionScreen(
//    viewModel: MainViewModel = hiltViewModel()
//){
//
//    Log.e("CHECK", "3 - MealSelectionScreen")
//    val openDialog = remember { mutableStateOf(false) }
//    val openMealsScreen = remember { mutableStateOf(false) }
//    val data = MutableStateFlow(CreatePlanDataState())
//    val dateFormatted by remember {
//        derivedStateOf {
//            DateTimeFormatter
//                .ofPattern("dd.MM")
//                .format(data.value.from.plusDays(data.value.currDay.toLong()))
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray),
//    ){
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 30.dp)
//                .background(Color.LightGray),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.Start
//        ) {
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ){
//
//                if(data.value.byDay.isNotEmpty()){
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(bottom = 50.dp),
//                        content = {
//                            items(data.value.byDay.size) { idx ->
//                                val meal = data.value.byDay[idx]
//                                Box(
//                                    modifier = Modifier.clickable {
//
//                                    }
//                                ) {
//                                    Row(
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(10.dp)
//                                    ) {
//                                        Text(text = (idx + 1).toString(), fontSize = 20.sp)
//                                        Text(text = if(meal.name.length > 18) meal.name.substring(0, 15) + "..." else meal.name , fontSize = 20.sp)
//                                        Button(
//                                            onClick = {  },
//                                            shape = RoundedCornerShape(15),
//                                        ) {
//                                            Text(text = "Remove")
//                                        }
//                                    }
//                                }
//                            }
//                        })
//
//                }
//
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ){
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly
//                ){
//                    Button(
//                        onClick = { openMealsScreen.value = true },
//                        shape = RoundedCornerShape(15),
//                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
//                        modifier = Modifier
//                            .height(40.dp)
//                            .width(150.dp)
//                    ) {
//                        Text(text = "Add meal")
//                    }
//                    Spacer(modifier = Modifier.size(8.dp))
//                    Button(
//                        onClick = { openDialog.value = true },
//                        shape = RoundedCornerShape(15),
//                        modifier = Modifier
//                            .height(40.dp)
//                            .width(150.dp)
//                    ) {
//                        Text(text = "Back")
//                    }
//                }
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ){
//
//                Button(
//                    enabled = data.value.currDay > 0,
//                    onClick = {
//
//                  },
//                    shape = RoundedCornerShape(15),
//                    modifier = Modifier
//                        .height(50.dp)
//                        .width(100.dp)
//                ) {
//                    Text(text = "Previous")
//                }
//
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ){
//
//                    val daysBetween = ChronoUnit.DAYS.between(data.value.from, data.value.to)
//
//                    Text(text = "${data.value.currDay+1}" + " out of " + daysBetween, fontSize = 25.sp)
//                    Text(text = dateFormatted, fontSize = 18.sp)
//                }
//
//                Button(
//                    onClick = {
//                        //Log.e("SSS", ChronoUnit.DAYS.between(data.value.from, data.value.to).toString() + "  " + (data.value.currDay.toLong() + 1))
//                        if(ChronoUnit.DAYS.between(data.value.from, data.value.to) == data.value.currDay.toLong() + 1){
////                            viewModel.onEvent(MainEvent.SetCreatePlanScreenState(CreatePlanScreenState.Email))
//                        }else{
////                            viewModel.onEvent(MainEvent.SetCreatePlanDataState(data.value.copy(currDay = data.value.currDay + 1)))
////                            viewModel.onEvent(MainEvent.LoadPlanedMealsByDay)
//                        }
//
//                  },
//                    shape = RoundedCornerShape(15),
//                    modifier = Modifier
//                        .height(50.dp)
//                        .width(100.dp)
//                ) {
//                    Text(text = "Next")
//                }
//
//            }
//        }
//    }
//
//    if(openMealsScreen.value){
//        MealsScreen(
//            callBack = {
//                openMealsScreen.value = false
//            },
//            onBack = {
//                openMealsScreen.value = false
//            }
//        )
//    }
//
//    if (openDialog.value) {
//        AlertDialog(
//            onDismissRequest = { openDialog.value = false },
//            title = { Text(text = "Warning!") },
//            text = { Text(text = "Data will be lost if you confirm action!") },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        openDialog.value = false
////                        viewModel.onEvent(MainEvent.SetCreatePlanDataState(CreatePlanDataState()))
////                        viewModel.onEvent(MainEvent.SetCreatePlanScreenState(CreatePlanScreenState.PeriodSelection))
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
//                ) {
//                    Text("Confirm")
//                }
//            },
//            dismissButton = {
//                Button( onClick = { openDialog.value = false })
//                {
//                    Text("Close")
//                }
//            }
//        )
//    }
//}


//@Composable
//private fun MealsScreen(
//    viewModel: MainViewModel = hiltViewModel(),
//    callBack: () -> Unit,
//    onBack: () -> Unit
//){
//
//    val data = MutableStateFlow(CreatePlanDataState())
//    val remote = remember { mutableStateOf(false) }
//    val local = remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray),
//    ){
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.LightGray),
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.LightGray),
//                ) {
//                    Button(
//                        onClick = {
////                            viewModel.onEvent(MainEvent.GetLocalMeals)
//                            local.value = true
//                        },
//                        shape = RoundedCornerShape(15),
//                        modifier = Modifier
//                            .height(50.dp)
//                            .width(100.dp)
//                    ) {
//                        Text(text = "Local")
//                    }
//                    Spacer(modifier = Modifier.size(20.dp))
//                    Button(
//                        onClick = {
//                            remote.value = true
//                        },
//                        shape = RoundedCornerShape(15),
//                        modifier = Modifier
//                            .height(50.dp)
//                            .width(100.dp)
//                    ) {
//                        Text(text = "Remote")
//                    }
//                }
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(
//                    onClick = {
//                        onBack.invoke()
//                    },
//                    shape = RoundedCornerShape(15),
//                    modifier = Modifier
//                        .height(50.dp)
//                        .width(100.dp)
//                ) {
//                    Text(text = "Back")
//                }
//            }
//
//        }
//
//        if(local.value){
//            ListLocalMealsScreen(
//                onSelection = {
//                    local.value = false
//                    callBack.invoke()
//                },
//                onBack = {
//                    local.value = false
//                 })
//        }
//
//        if(remote.value){
//            ListCategories(
//                callBack = {
//                    remote.value = false
//                    callBack.invoke()
//                },
//                onBack = { remote.value = false }
//            )
//        }
//    }
//}

@Composable
private fun ListCategories(
    viewModel: MainViewModel = hiltViewModel(),
    callBack: () -> Unit,
    onBack: () -> Unit
){

//    val energy = viewModel.mealsScreenEnergyData.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val listMealsScreen = remember { mutableStateOf(false) }
    val categoryDescription = remember { mutableStateOf("") }

//    if(energy.value.categories != null) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.LightGray),
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 30.dp)
//                    .background(Color.LightGray),
//                verticalArrangement = Arrangement.SpaceBetween,
//                horizontalAlignment = Alignment.Start
//            ) {
//                LazyColumn(
//                    modifier = Modifier.weight(1f).padding(bottom = 30.dp),
//                    content = {
//                    items(energy.value.categories!!.size) { idx ->
//                        val category = energy.value.categories!![idx]
//                        Box(
//                            modifier = Modifier.clickable {
////                                viewModel.onEvent(MainEvent.FetchRemoteMealsByCategory(category = category.name))
//                                listMealsScreen.value = true
//                            }
//                        ) {
//                            Row(
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(10.dp)
//                            ) {
//                                LoadImageFromUrl(url = category.imageUri)
//                                Text(text = category.name, fontSize = 20.sp)
//                                Icon(
//                                    imageVector = Icons.Filled.MoreVert,
//                                    contentDescription = "Overflow Menu",
//                                    modifier = Modifier.clickable {
//                                        categoryDescription.value = category.des
//                                        openDialog.value = true
//                                    }
//                                )
//                            }
//                        }
//                    }
//                })
//
//                Row(
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth()
//                ){
//                    Button(
//                        onClick = { onBack.invoke() },
//                        shape = RoundedCornerShape(15),
//                        modifier = Modifier
//                            .height(50.dp)
//                            .width(100.dp)
//                    ) {
//                        Text(text = "Back")
//                    }
//                }
//            }
//        }
//    }

    if(listMealsScreen.value){
        ListMealsScreenByCategory(
            onBack = { listMealsScreen.value = false },
            onSelection = {
                listMealsScreen.value = false
                callBack.invoke()
            })
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Category description")
            },
            text = {
                Text(text = categoryDescription.value)
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun ListMealsScreenByCategory(
    viewModel: MainViewModel = hiltViewModel(),
    onSelection: () -> Unit,
    onBack: () -> Unit
){

//    val energy = viewModel.mealsScreenEnergyData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 30.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
//            if(energy.value.remoteMealRemoteEntities.isNotEmpty()){
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(bottom = 30.dp),
//                    content = {
//                        items(energy.value.remoteMealRemoteEntities.size) { idx ->
//                            val meal = energy.value.remoteMealRemoteEntities[idx]
//                            Box(
//                                modifier = Modifier.clickable {
////                                    viewModel.onEvent(MainEvent.InsertToPlanFromRemote(mealId = meal.remoteIdMeal))
//                                    onSelection.invoke()
//                                }
//                            ) {
//                                Row(
//                                    horizontalArrangement = Arrangement.Start,
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(
//                                            start = 10.dp,
//                                            top = 3.dp,
//                                            end = 10.dp,
//                                            bottom = 3.dp
//                                        )
//                                ) {
//                                    AsyncImage(
//                                        model =  meal.imageUri,
//                                        contentDescription = "description",
//                                        modifier = Modifier
//                                            .size(100.dp)
//                                            .padding(end = 15.dp)
//                                    )
//                                    Text(text = meal.name, fontSize = 20.sp)
//                                }
//                            }
//                        }
//                    }
//                )
//            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
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


@Composable
private fun ListLocalMealsScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onSelection: () -> Unit,
    onBack: () -> Unit
){

//    val energy = viewModel.mealsScreenEnergyData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 30.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
//            if(energy.value.localMealRemoteEntities.isNotEmpty()){
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(bottom = 30.dp),
//                    content = {
//                        items(energy.value.localMealRemoteEntities.size) { idx ->
//                            val meal = energy.value.localMealRemoteEntities[idx]
//                            Box(
//                                modifier = Modifier.clickable {
////                                    viewModel.onEvent(MainEvent.InsertToPlanFromLocal(mealId = meal.remoteIdMeal))
//                                    onSelection.invoke()
//                                }
//                            ) {
//                                Row(
//                                    horizontalArrangement = Arrangement.Start,
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(
//                                            start = 10.dp,
//                                            top = 3.dp,
//                                            end = 10.dp,
//                                            bottom = 3.dp
//                                        )
//                                ) {
//                                    AsyncImage(
//                                        model =  meal.imageUri,
//                                        contentDescription = "description",
//                                        modifier = Modifier
//                                            .size(100.dp)
//                                            .padding(end = 15.dp)
//                                    )
//                                    Text(text = meal.name, fontSize = 20.sp)
//                                }
//                            }
//                        }
//                    }
//                )
//            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
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