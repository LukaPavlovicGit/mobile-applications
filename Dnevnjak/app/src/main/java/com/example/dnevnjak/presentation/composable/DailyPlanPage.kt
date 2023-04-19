package com.example.dnevnjak.presentation.composable

import android.annotation.SuppressLint
import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.DnevnjakEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import com.example.dnevnjak.utilities.Priority
import com.example.dnevnjak.utilities.Utility
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter




@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DailyPlanPage(
    viewModel: MainViewModel
){

    val pagerState = rememberPagerState(initialPage = 0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(Color(rgb(178, 235, 242)))
    ) {
            Header(viewModel = viewModel)
            ShowPastObligationRow(viewModel = viewModel)
            Divider(color = Color.Black, thickness = 1.dp)
            ShowAllObligationRow(viewModel = viewModel)
            Divider(color = Color.Black, thickness = 1.dp)
            SearchQueryRow(viewModel = viewModel)
            ObligationData(viewModel = viewModel, pagerState = pagerState)
            TabsContent(viewModel = viewModel, pagerState = pagerState)
        }
    }

@Composable
private fun Header(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()

    Row(
        modifier = Modifier
            .background(PRIMARY_COLOR)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ){
        Text(
            text = Utility.fullDateFormatterStr(dailyPlanState.date),
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
private fun ShowPastObligationRow(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(rgb(178, 235, 242)))
    ){
        Text(
            text = "Show past obligations",
            color = Color(rgb(13, 71, 161)),
            fontSize = 23.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
        )
        Checkbox(
            checked = dailyPlanState.showPastObligations,
            onCheckedChange = {
                viewModel.onEvent(DnevnjakEvent.ShowPastObligations)
            },
            colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black
                    )
        )
    }
}

@Composable
private fun ShowAllObligationRow(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(rgb(178, 235, 242)))
    ){
        Text(
            text = "Show all obligations",
            color = Color(rgb(13, 71, 161)),
            fontSize = 23.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
        )
        Checkbox(
            checked = dailyPlanState.showAllObligations,
            onCheckedChange = {
                viewModel.onEvent(DnevnjakEvent.ShowAllDailyPlans)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )
    }
}

@Composable
private fun SearchQueryRow(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = dailyPlanState.searchText,
            onValueChange = { viewModel.onEvent(DnevnjakEvent.SetSearchQuery(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(rgb(179, 229, 252)))
                .padding(start = 5.dp, top = 10.dp, end = 5.dp, bottom = 10.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = { Text(text = "Search", fontSize = 18.sp) },
            textStyle = TextStyle.Default.copy(fontSize = 20.sp)
        )
        if(dailyPlanState.isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {

        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun ObligationData(
    viewModel: MainViewModel,
    pagerState: PagerState
){
    val list = remember { listOf("High","Mid","Low" ) }
    val colors = remember { listOf(Color.Red, Color.Yellow, Color.Green) }
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = Color.Black,
        indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
//                                height = 4.dp,
//                                color = Color.White,
                            )
                    }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = { Text(text = list[index], fontSize = 20.sp) },
                modifier = Modifier
                    .background(colors[index])
                    .border(2.dp, Color.Black),
                selected = pagerState.currentPage == index ,
                onClick = {
                    when(index){
                        0 -> viewModel.onEvent(DnevnjakEvent.FilterByPriority(Priority.High))
                        1 -> viewModel.onEvent(DnevnjakEvent.FilterByPriority(Priority.Mid))
                        2 -> viewModel.onEvent(DnevnjakEvent.FilterByPriority(Priority.Low))
                    }
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(
    viewModel: MainViewModel,
    pagerState: PagerState
){
    HorizontalPager(count = 3, state = pagerState) { page ->
        when (page) {
            0 -> HighTab(viewModel = viewModel)
            1 -> MidTab(viewModel = viewModel)
            2 -> LowTab(viewModel = viewModel)
        }
    }
}

@Composable
fun LowTab(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()
    val currDate = remember { LocalDate.now() }
    val currTime = remember { LocalTime.now() }
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(rgb(178, 235, 242))),
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(dailyPlanState.obligations.size){ idx ->
                val obligation = dailyPlanState.obligations[idx]
                Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(DnevnjakEvent.SelectedObligation(obligation))
                        }
                        .height(90.dp)
                        .border(1.dp, Color.Black)
                        .background(
                            if (currDate.isAfter(obligation.date) || (currDate.isEqual(obligation.date) && currTime.isAfter(
                                    obligation.end
                                ))
                            )
                                Color(rgb(97, 97, 97))
                            else
                                Color(rgb(178, 235, 242))
                        ),
                    contentAlignment = Alignment.Center
                ){
                    ObligationView(viewModel = viewModel, obligationEntity = obligation)
                }
            }
        }
    )
}

@Composable
fun MidTab(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()
    val currDate = remember { LocalDate.now() }
    val currTime = remember { LocalTime.now() }
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(rgb(178, 235, 242))),
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 30.dp),
        content = {
            items(dailyPlanState.obligations.size){ idx ->
                val obligation = dailyPlanState.obligations[idx]
                Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(DnevnjakEvent.SelectedObligation(obligation))
                        }
                        .height(90.dp)
                        .border(1.dp, Color.Black)
                        .background(
                            if (currDate.isAfter(obligation.date) || (currDate.isEqual(obligation.date) && currTime.isAfter(
                                    obligation.end
                                ))
                            )
                                Color(rgb(97, 97, 97))
                            else
                                Color(rgb(178, 235, 242))
                        ),
                    contentAlignment = Alignment.Center
                ){
                    ObligationView(viewModel = viewModel, obligationEntity = obligation)
                }

            }
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HighTab(
    viewModel: MainViewModel
){
    val dailyPlanState by viewModel.dailyPlanState.collectAsState()
    val currDate = remember { LocalDate.now() }
    val currTime = remember { LocalTime.now() }
    val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(rgb(178, 235, 242))),
            state = state,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 30.dp),
            content = {
                items(dailyPlanState.obligations.size){ idx ->
                    val obligation = dailyPlanState.obligations[idx]
                    Box(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(DnevnjakEvent.SelectedObligation(obligation))
                            }
                            .height(90.dp)
                            .border(1.dp, Color.Black)
                            .background(
                                if (currDate.isAfter(obligation.date) || (currDate.isEqual(
                                        obligation.date
                                    ) && currTime.isAfter(obligation.end))
                                )
                                    Color(rgb(97, 97, 97))
                                else
                                    Color(rgb(178, 235, 242))
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        ObligationView(viewModel = viewModel, obligationEntity = obligation)
                    }
                }
            }
        )
}

@Composable
private fun ObligationView(
    viewModel: MainViewModel,
    obligationEntity: ObligationEntity
){

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val start = obligationEntity.start.format(formatter)
    val end = obligationEntity.end.format(formatter)
    val title = obligationEntity.title
    val priority = obligationEntity.priority

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ){

        Icon(
            imageVector = Icons.Default.EventNote
//                when(priority){
//                    Priority.High -> Icons.Default.BrightnessHigh
//                    Priority.Mid -> Icons.Default.BrightnessMedium
//                    Priority.Low ->Icons.Default.BrightnessLow
//                }
            ,
            contentDescription = "Navigation Icon",
            modifier = Modifier
                .size(60.dp)
                .background(
                    when (priority) {
                        Priority.High -> Color.Red
                        Priority.Mid -> Color.Yellow
                        Priority.Low -> Color.Green
                    }
                )
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(start = 20.dp)//.background(Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()//.background(Color.Red)
            ){
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Navigation Icon",
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "$start - $end",
                    color = Color.Black,
                    fontSize = 22.sp,
                )
            }
            Text(
                text = title,
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 7.dp, top = 7.dp)//.background(Color.Blue)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()//.background(Color.Yellow)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                modifier = Modifier
                    .size(35.dp)
                    .weight(1f)
                    .clickable { viewModel.onEvent(DnevnjakEvent.EditObligation) }
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .size(35.dp)
                    .weight(1f)
                    .clickable { viewModel.onEvent(DnevnjakEvent.DeleteObligation) }
            )
        }
    }
}