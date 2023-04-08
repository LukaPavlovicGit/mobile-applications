package com.example.dnevnjak.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.states.ObligationState
import com.example.dnevnjak.presentation.viewModels.ObligationViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun CalendarPage(
    viewModel: ObligationViewModel = koinViewModel(),
    state: ObligationState,
    onEvent: (ObligationEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(PRIMARY_COLOR),
        contentAlignment = Alignment.Center
    ){
        Column() {
            HeaderView(viewModel = viewModel, state = state, onEvent = onEvent)
            CalendarView(viewModel = viewModel, state = state, onEvent = onEvent )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HeaderView(
    viewModel: ObligationViewModel,
    state: ObligationState,
    onEvent: (ObligationEvent) -> Unit
){

    Column( horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = viewModel.headerDate.value,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for(i in 1..7){
                val day = when(i){
                    1 -> "Mon"
                    2 -> "Tue"
                    3 -> "Wen"
                    4 -> "Thu"
                    5 -> "Fri"
                    6 -> "Sat"
                    else -> "Sun"
                }
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text( text = day,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarView(
    viewModel: ObligationViewModel,
    state: ObligationState,
    onEvent: (ObligationEvent) -> Unit
){

    val latest: LocalDate = remember { LocalDate.now().minusMonths(1) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        content = {
            items(10000) { i ->
                val current: LocalDate = latest.plusDays(i.toLong())
                val color: Color = when(state.dayColorMap[current]){
                    null -> Color.White
                    else -> state.dayColorMap[current]!!
                }
                Box(
                    modifier = Modifier
                        .clickable { Log.e("DATE", current.toString()) }
                        .height(113.dp)
                        .border(1.dp, Color.Black)
                        .background(color),
                    contentAlignment = Alignment.Center,
                ) {
                    Text( text = "${current.dayOfMonth}.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(10.dp)
                    )
                }
            }
        }
    )
}

