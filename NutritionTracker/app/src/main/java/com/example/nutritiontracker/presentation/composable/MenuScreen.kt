package com.example.nutritiontracker.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadImageFromUrl
import com.example.nutritiontracker.presentation.composable.cammon.SearchMeals
import com.example.nutritiontracker.states.screens.RemoteMenuScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MenuScreen(
    viewModel: MainViewModel = viewModel()
){
    TabScreen(viewModel = viewModel)
}

@Composable
private fun TabScreen(
    viewModel: MainViewModel = viewModel()
) {
    val tabOptions = listOf("Remote", "Local")
    val selectedTabIndex = remember { mutableStateOf(viewModel.navigationData.lastMenuScreenTabIdx) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.height(40.dp),
            backgroundColor = Color.DarkGray,
            contentColor = Color.White
        ) {
            tabOptions.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        viewModel.onEvent(MainEvent.SetNavigationData(viewModel.navigationData.copy(lastMenuScreenTabIdx = index)))
                        selectedTabIndex.value = index
                    }
                ) {
                    Text(text = title)
                }
            }
        }

        when (selectedTabIndex.value) {
            0 -> RemoteMenuScreen(viewModel = viewModel)
            1 -> LocalMenuScreen(viewModel = viewModel)
        }
    }
}


@Composable
fun RemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){
    val energy = viewModel.menuScreenEnergyData.collectAsState()
    when(energy.value.categories){
        null -> { Log.e("MenuScreen", "MENU SCREEN DON'T HAVE ENERGY") }
        else -> {
            EnergizeRemoteMenuScreen(viewModel = viewModel)
        }
    }
}

@Composable
private fun EnergizeRemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){

    val remoteMenuScreenState = viewModel.remoteMenuScreenState.collectAsState()
    Log.e("ENTERED", "TRUE")
    when(remoteMenuScreenState.value){
        RemoteMenuScreenState.Default -> DefaultRemoteMenuScreen(viewModel = viewModel)
        RemoteMenuScreenState.Error -> TODO()
    }
}

@Composable
private fun DefaultRemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        SearchMeals(
            placeholder = "search by name",
            callBack = {
                viewModel.onEvent(MainEvent.SearchMealsByName(name = it))
            }
        )
        SearchMeals(
            placeholder = "search by ingredient",
            callBack = {
                viewModel.onEvent(MainEvent.SearchMealsByIngredient(ingredient = it))
            }
        )
        ListCategories(viewModel = viewModel)
    }
}


@Composable
private fun ListCategories(
    viewModel: MainViewModel = viewModel()
){

    val energy = viewModel.menuScreenEnergyData.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val categoryDescription = remember { mutableStateOf("") }

    LazyColumn(content = {
        items(energy.value.categories!!.size){ idx->
            val category = energy.value.categories!![idx]
            Box(
                modifier = Modifier.clickable {
                    viewModel.onEvent(MainEvent.CategorySelection(category = category))
                }
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ){
                    LoadImageFromUrl(url = category.imageUri)
                    Text(text = category.name, fontSize = 20.sp)
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
private fun LocalMenuScreen(
    viewModel: MainViewModel = viewModel()
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        DropDownMealName(viewModel = viewModel)
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealType(viewModel = viewModel)
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealCategory(viewModel = viewModel)
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealArea(viewModel = viewModel)
        Spacer(modifier = Modifier.size(10.dp))
        DropDownMealIngredient(viewModel = viewModel)

        Spacer(modifier = Modifier.size(100.dp))
        Button(
            onClick = { viewModel.onEvent(MainEvent.LocalSearchFilter) },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = { viewModel.onEvent(MainEvent.GetSavedMeals) },
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
    viewModel: MainViewModel = viewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }
    val options = listOf(MealType.Breakfast.name,MealType.Launch.name,MealType.Dinner.name,MealType.Snack.name)

    val localSearchFilters = viewModel.localStorageFilters.collectAsState()

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
                    viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(mealTypeName = opt)))
                }) {
                    Text(opt)
                }
            }
        }
        TextField(
            value = localSearchFilters.value.mealTypeName,
            label = { Text(text = "meal type") },
            placeholder = { Text(text = "meal type") },
            onValueChange = { viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(mealTypeName = it))) },
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
    viewModel: MainViewModel = viewModel()
){

    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }
    val localSearchFilters = viewModel.localStorageFilters.collectAsState()

    Row(modifier = Modifier.fillMaxWidth()){

        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }

        TextField(
            value = localSearchFilters.value.name,
            label = { Text(text = "name") },
            placeholder = { Text(text = "name") },
            onValueChange = { viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(name = it))) },
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
    viewModel: MainViewModel = viewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    val localSearchFilters = viewModel.localStorageFilters.collectAsState()
    val energy = viewModel.filterScreenEnergyData.value


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
            if(energy.categoryNames != null) {
                energy.categoryNames.forEach() { opt ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(category = opt)))
                    }) {
                        Text(opt)
                    }
                }
            }
        }
        TextField(
            value = localSearchFilters.value.category,
            label = { Text(text = "category") },
            placeholder = { Text(text = "category") },
            onValueChange = { viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(category = it))) },
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
    viewModel: MainViewModel = viewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    val localSearchFilters = viewModel.localStorageFilters.collectAsState()
    val energy = viewModel.filterScreenEnergyData.value


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
            if(energy.ingredientsNames != null) {
                energy.ingredientsNames.forEach { opt ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(ingredient = opt)))
                    }) {
                        Text(opt)
                    }
                }
            }
        }
        TextField(
            value = localSearchFilters.value.ingredient,
            label = { Text(text = "ingredient") },
            placeholder = { Text(text = "ingredient") },
            onValueChange = { viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(ingredient = it))) },
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
    viewModel: MainViewModel = viewModel()
){

    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    val localSearchFilters = viewModel.localStorageFilters.collectAsState()
    val energy = viewModel.filterScreenEnergyData.value


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
            if(energy.areaNames != null) {
                energy.areaNames.forEach { opt ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(area = opt)))
                    }) {
                        Text(opt)
                    }
                }
            }
        }
        TextField(
            value = localSearchFilters.value.area,
            label = { Text(text = "area") },
            placeholder = { Text(text = "area") },
            onValueChange = { viewModel.onEvent(MainEvent.SetLocalSearchFilters(localSearchFilters.value.copy(area = it))) },
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



