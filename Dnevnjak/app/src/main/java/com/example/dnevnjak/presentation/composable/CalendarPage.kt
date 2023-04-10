package com.example.dnevnjak.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.CalendarEvent
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.states.ObligationState
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun CalendarPage(
    viewModel: MainViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PRIMARY_COLOR),
        contentAlignment = Alignment.Center
    ){
        Column {
            HeaderView(viewModel = viewModel)
            CalendarView(viewModel = viewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HeaderView(
    viewModel: MainViewModel
){
    val calendarState = viewModel.calendarState.collectAsState()
    val headerDate by viewModel.headerDate.collectAsState()

    Column( horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = headerDate, // why does not work with 'calendarState.value.headerDate'
            fontSize = 35.sp,
            fontWeight = FontWeight.Normal,
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text( text = day,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarView(
    viewModel: MainViewModel
){
    val calendarState = viewModel.calendarState.collectAsState()
    val gridState = rememberLazyGridState()
    val earliest: LocalDate = remember { LocalDate.now().minusMonths(1) }

    LazyVerticalGrid(
        modifier = Modifier,
        state = gridState,
        columns = GridCells.Fixed(7),
        content = {
            items(1000) { i ->
                val current: LocalDate = earliest.plusDays(i.toLong())
                val animatedBlur by animateDpAsState(
                    targetValue = if(
                        current.monthValue == calendarState.value.displayedMonth &&
                        current.year == calendarState.value.displayedYear
                    )
                        0.dp
                    else
                        15.dp
                )
                val color: Color = when(calendarState.value.dayColorMap[current]){
                    null -> Color.White
                    else -> calendarState.value.dayColorMap[current]!!
                }
                Box(
                    modifier = Modifier
                        .clickable {
                            Log.e("DATE:", current.toString())
                        }
                        .blur(radius = animatedBlur)
                        .height(113.dp)
                        .border(1.dp, Color.Black)
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text( text = "${current.dayOfMonth}.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(10.dp)
                    )
                }

                val lowerBoundL = earliest.plusDays(gridState.firstVisibleItemIndex.toLong() + 7L)
                val upperBoundL = earliest.plusDays(gridState.firstVisibleItemIndex.toLong() + 7L + 21L)
                val lowerBoundR = earliest.plusDays(gridState.firstVisibleItemIndex.toLong() + 7L + 6L)
                val upperBoundR = earliest.plusDays(gridState.firstVisibleItemIndex.toLong() + 7L + 21L + 6L)

                if(lowerBoundL.dayOfMonth <= upperBoundL.dayOfMonth || lowerBoundR.dayOfMonth <= upperBoundR.dayOfMonth)
                    viewModel.onCalendarEvent(CalendarEvent.SetDisplayedDate(lowerBoundR))

            }
        }
    )
}
