package com.example.nutritiontracker.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.events.MenuScreenEvent
import com.example.nutritiontracker.viewModel.MainViewModel
import com.example.nutritiontracker.viewModel.MenuViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
){

    val categories = menuViewModel.categories.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val categoryDescription = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val showListMeals = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val mealsState = menuViewModel.mealsState

    when{
        showListMeals.value -> {
            mainViewModel.shouldShowNavigationBar.value = false
            MealList(
                mealsState = mealsState,
                modifier = Modifier.fillMaxSize().background(Color.Black),
                onBack = {
                    mainViewModel.shouldShowNavigationBar.value = true
                    showListMeals.value = false
                }
            )
        }
        else -> {
            Box( modifier = Modifier.fillMaxSize()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ){
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ){
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                IconButton(
                                    onClick = { expanded.value = !expanded.value },
                                    modifier = Modifier.padding(end = 8.dp)
                                ){
                                    if(!expanded.value){
                                        Icon(
                                            imageVector = Icons.Default.ArrowDownward,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier
                                                .background(Color.Black)
                                                .clip(RoundedCornerShape(50)),
                                            tint = Color.White
                                        )
                                    }
                                    else{
                                        Icon(
                                            imageVector = Icons.Default.ArrowUpward,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier
                                                .background(Color.Black)
                                                .clip(RoundedCornerShape(50)),
                                            tint = Color.White
                                        )
                                    }
                                }
                                if(!expanded.value){
                                    Text(text = "Search", fontSize = 18.sp)
                                }else{
                                    Text(text = "Select criteria", fontSize = 18.sp)
                                }
                            }
                            AnimatedVisibility(visible = expanded.value) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.Start
                                ){
                                    val radioOptions = listOf("By name", "By area", "By ingredient", "By tags")
                                    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
                                    Column(
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start,
                                    ) {
                                        radioOptions.forEach { opt ->
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .padding(0.dp)
                                                    .selectable(
                                                        selected = (opt == selectedOption),
                                                        onClick = { onOptionSelected(opt) }
                                                    )
                                            ){
                                                RadioButton(
                                                    selected = (opt == selectedOption),
                                                    onClick = { onOptionSelected(opt) }
                                                )
                                                Text(
                                                    text = opt,
                                                    style = MaterialTheme.typography.body1.merge(),
                                                )
                                                if(opt == "By area"){
                                                    IconButton(
                                                        onClick = { },
                                                        modifier = Modifier.padding(end = 8.dp)
                                                    ){
                                                        Icon(
                                                            imageVector = Icons.Default.ArrowDropDown,
                                                            contentDescription = "Dropdown",
                                                            modifier = Modifier
                                                                .background(Color.Black)
                                                                .clip(RoundedCornerShape(50)),
                                                            tint = Color.White
                                                        )
                                                    }
                                                }
                                                if(opt == "By ingredient"){
                                                    IconButton(
                                                        onClick = { },
                                                        modifier = Modifier.padding(end = 8.dp)
                                                    ){
                                                        Icon(
                                                            imageVector = Icons.Default.ArrowDropDown,
                                                            contentDescription = "Dropdown",
                                                            modifier = Modifier
                                                                .background(Color.Black)
                                                                .clip(RoundedCornerShape(50)),
                                                            tint = Color.White
                                                        )
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ){
                                        TextField(
                                            value = searchText.value,
                                            onValueChange = { searchText.value = it },
                                            placeholder = { Text(text = "placeholder") },
                                            singleLine = true,
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier
                                                .fillMaxWidth(0.6f)
                                                .padding(bottom = 8.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = Color.White,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            )
                                        )
                                        Button(
                                            onClick = {
                                                when(selectedOption){
                                                    "By name" -> {
                                                        menuViewModel.onEvent(MenuScreenEvent.SearchMealsByName(searchText.value))
                                                        showListMeals.value = true
                                                    }
                                                    "By area" -> {
                                                        menuViewModel.onEvent(MenuScreenEvent.SearchMealsByArea(searchText.value))
                                                        showListMeals.value = true
                                                    }
                                                    "By ingredient" -> {
                                                        menuViewModel.onEvent(MenuScreenEvent.SearchMealsByIngredient(searchText.value))
                                                        showListMeals.value = true
                                                    }
                                                    "By tags" -> {
                                                        menuViewModel.onEvent(MenuScreenEvent.SearchMealsByTags(searchText.value))
                                                        showListMeals.value = true
                                                    }
                                                }
                                            },
                                            shape = RoundedCornerShape(6.dp),
                                        ) {
                                            Text(text = "Search", fontSize = 20.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    LazyColumn(content = {
                        items(categories.value.size){ idx ->
                            val category = categories.value[idx]
                            Box(
                                modifier = Modifier.clickable {
                                    menuViewModel.onEvent(MenuScreenEvent.SearchMealsByCategory(category.name))
                                    showListMeals.value = true
                                }
                            ){
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
                                        AsyncImage(
                                            model = category.imageUri,
                                            contentDescription = "description",
                                            modifier = Modifier.padding(end = 30.dp)
                                        )
                                        Text(text = category.name, fontSize = 20.sp)
                                    }
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = "Overflow Menu",
                                        modifier = Modifier.clickable {
                                            categoryDescription.value = category.des
                                            openDialog.value = true
                                        }
                                    )
                                }
                            }
                        }
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
        }
    }
}

@Composable
private fun LocalMenuScreen(
    viewModel: MainViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        DropDownMealName()
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealType()
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealCategory()
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealArea()
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealIngredient()

        Spacer(modifier = Modifier.size(100.dp))
        Button(
            onClick = {
//                viewModel.onEvent(MainEvent.LocalSearchFilter)
                      },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
//                viewModel.onEvent(MainEvent.GetSavedMeals)
                      },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Get All", fontSize = 20.sp)
        }
    }
}


@Composable
private fun DropDownMealType(
    viewModel: MainViewModel = hiltViewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }
    val options = listOf(MealType.Breakfast.name,MealType.Launch.name,MealType.Dinner.name,MealType.Snack.name)

//    val localSearchFilters = viewModel.localStorageFilters.collectAsState()

    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = { expanded.value = true }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { opt ->
                DropdownMenuItem(onClick = {
                    expanded.value = false
//                    viewModel.onEvent(
//                        MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(mealTypeName = opt))
//                    )
                }) {
                    Text(opt)
                }
            }
        }
        TextField(
            value = "KITA",
            label = { Text(text = "meal type") },
            placeholder = { Text(text = "meal type") },
            onValueChange = {
//                viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(mealTypeName = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )

    }
}

@Composable
private fun DropDownMealName(
    viewModel: MainViewModel = hiltViewModel()
){

    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        TextField(
            value = "KITA",
            label = { Text(text = "name") },
            placeholder = { Text(text = "name") },
            onValueChange = {
//                viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(name = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DropDownMealCategory(
    viewModel: MainViewModel = hiltViewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = { expanded.value = true }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
//            if(energy.categoryNames != null) {
//                energy.categoryNames.forEach() { opt ->
//                    DropdownMenuItem(onClick = {
//                        expanded.value = false
////                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(category = opt)))
//                    }) {
//                        Text(opt)
//                    }
//                }
//            }
        }
        TextField(
            value = "KITA",
            label = { Text(text = "category") },
            placeholder = { Text(text = "category") },
            onValueChange = {
//                viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(category = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DropDownMealIngredient(
    viewModel: MainViewModel = hiltViewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }



    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = { expanded.value = true }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
//            if(energy.ingredientsNames != null) {
//                energy.ingredientsNames.forEach { opt ->
//                    DropdownMenuItem(onClick = {
//                        expanded.value = false
////                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(ingredient = opt)))
//                    }) {
//                        Text(opt)
//                    }
//                }
//            }
        }
        TextField(
            value = "KITA",
            label = { Text(text = "ingredient") },
            placeholder = { Text(text = "ingredient") },
            onValueChange = {
//                viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(ingredient = it)))
                            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DropDownMealArea(
    viewModel: MainViewModel = hiltViewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = { expanded.value = true }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
//            if(energy.areaNames != null) {
//                energy.areaNames.forEach { opt ->
//                    DropdownMenuItem(onClick = {
//                        expanded.value = false
////                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(area = opt)))
//                    }) {
//                        Text(opt)
//                    }
//                }
//            }
        }
        TextField(
            value = "KITA",
            label = { Text(text = "area") },
            placeholder = { Text(text = "area") },
            onValueChange = {
//                viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(area = it)))
                            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
    }
}



