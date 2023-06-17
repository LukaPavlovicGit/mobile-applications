package com.example.nutritiontracker.presentation.composable.cammon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun SearchMeals(
    viewModel: MainViewModel = viewModel(),
    placeholder: String,
    callBack: (String) -> Unit
){
    val textValue = remember { mutableStateOf(TextFieldValue()) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        TextField(
            value = textValue.value,
            onValueChange = { textValue.value = it },
            placeholder = { Text(text = placeholder) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.6f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Button(
            onClick = { callBack.invoke(textValue.value.text) },
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
    }
}