package com.example.nutritiontracker.presentation.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutritiontracker.viewModel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.presentation.composable.cammon.toast

@Composable
fun StatsScreen(
    viewModel: MainViewModel = hiltViewModel()
){

    val chartDataState = viewModel.chartDataState.collectAsState()

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
            Row(
                modifier = Modifier.background(Color.LightGray).height(300.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ){
                for ((key, value) in chartDataState.value.mock) {
                    CustomBar(viewModel = viewModel, size = (value * 10).dp, realSize = value, dayNum = key)

                }
            }

            Row(
                modifier = Modifier.background(Color.LightGray),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                for ((key, value) in chartDataState.value.mock) {
                    Text(text = key.toString())
                }
            }


        }
    }
}



@Composable
fun RowScope.CustomBar(
    viewModel: MainViewModel = viewModel(),
    size: Dp,
    realSize: Int,
    dayNum: Int
) {
    val context = LocalContext.current
    val height = remember { mutableStateOf(0.dp) }
    val heightStateAnimate = animateDpAsState(
        targetValue = height.value,
        tween(durationMillis = 2000, delayMillis = 300, easing = LinearEasing)
    )

    LaunchedEffect(key1 = size){
        height.value = size
    }

    Box(
        modifier = Modifier
            .padding(start = 4.dp, bottom = 0.dp, end = 4.dp, top = 4.dp)
            .size(heightStateAnimate.value)
            .weight(1f)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Color.Red)
            .clickable {
                toast(context = context, message = "Value: $realSize")
            },
    )
    
}
