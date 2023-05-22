package com.example.raftrading.features.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.raftrading.common.CircularIndeterminateProgressBar
import com.example.raftrading.common.toast
import com.example.raftrading.features.UiState

@Composable
fun DiscoveryScreen(
    onNewsClicked: (String?) -> Unit,
    onStockClicked: (StockModel) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(Color.Black)
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .align(Alignment.TopCenter)
        ) {
            HorizontalCardList(onNewsClicked = onNewsClicked)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(Color.White)
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .align(Alignment.BottomCenter)
        ) {
            VerticalCardList(onStockClicked = onStockClicked)
        }

    }
}

@Composable
fun HorizontalCardList(
    viewModel: DiscoveryViewModel = viewModel(),
    onNewsClicked: (String?) -> Unit
) {

    val newsUiState = viewModel.newsUiState.collectAsState()
    when(newsUiState.value){
        is UiState.Failure -> {
            toast(LocalContext.current, (newsUiState.value as UiState.Failure).message)
            viewModel.onEvent(DiscoveryEvent.ResetUiState)
        }
        UiState.Nothing -> { }
        UiState.Processing -> CircularIndeterminateProgressBar()
        is UiState.Success -> NewsHolder(onNewsClicked = onNewsClicked)
    }
}

@Composable
private fun NewsHolder(
    viewModel: DiscoveryViewModel = viewModel(),
    onNewsClicked: (String?) -> Unit
){
    val discoveryDataState = viewModel.discoveryDataState.collectAsState()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        content = {
            val allNews = discoveryDataState.value.news?.newsData?.newsItems!!
            items(allNews.size) { idx ->
                val news = allNews[idx]
                Box(
                    modifier = Modifier
                        .width(400.dp)
                        .height(400.dp)
                        .padding(8.dp)
                        .clickable { onNewsClicked.invoke(news.link) }

                ){
                    Card(
                        modifier = Modifier
                            .width(400.dp)
                            .height(400.dp)
                    ) {
                        val painter = rememberAsyncImagePainter(model = news.image)
                        Column (
                            modifier = Modifier
                                .width(400.dp)
                                .height(400.dp)
                                .paint(painter = painter)
                            ,
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                text = news.date.toString(),
                                style = TextStyle(fontSize = 16.sp),
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = news.title.toString(),
                                style = TextStyle(fontSize = 16.sp),
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun VerticalCardList(
    viewModel: DiscoveryViewModel = viewModel(),
    onStockClicked: (StockModel) -> Unit
){
    val stocksUiState = viewModel.stocksUiState.collectAsState()
    when(stocksUiState.value){
        is UiState.Failure -> {
            toast(LocalContext.current, (stocksUiState.value as UiState.Failure).message)
            viewModel.onEvent(DiscoveryEvent.ResetUiState)
        }
        UiState.Nothing -> { }
        UiState.Processing -> CircularIndeterminateProgressBar()
        is UiState.Success -> StocksHolder(onStocksClicked = onStockClicked)
    }
}

@Composable
private fun StocksHolder(
    viewModel: DiscoveryViewModel = viewModel(),
    onStocksClicked: (StockModel) -> Unit
){
    val discoveryDataState = viewModel.discoveryDataState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        content = {
            val allStocks = discoveryDataState.value.stocks
            items(allStocks.size) { idx ->
                val stock = allStocks[idx]
                Box(
                    modifier = Modifier
                        .width(400.dp)
                        .height(400.dp)
                        .padding(8.dp)
                        .clickable { onStocksClicked.invoke(stock) }

                ){
                    Card(
                        modifier = Modifier
                            .width(400.dp)
                            .height(400.dp)
                    ) {

                        Column (
                            modifier = Modifier
                                .width(400.dp)
                                .height(400.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                text = stock.data.symbol,
                                style = TextStyle(fontSize = 16.sp),
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = stock.data.name,
                                style = TextStyle(fontSize = 16.sp),
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = stock.data.last.toString(),
                                style = TextStyle(fontSize = 16.sp),
                                textAlign = TextAlign.Left
                            )
                        }
                    }
                }
            }
        }
    )
}
