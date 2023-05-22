package com.example.raftrading.features.discovery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raftrading.data.NewsRepository
import com.example.raftrading.data.StocksRepository
import com.example.raftrading.features.UiState
import com.example.raftrading.states.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val stocksRepository: StocksRepository,
): ViewModel() {

    private val _discoveryDataState = MutableStateFlow(DiscoveryDataState())
    val discoveryDataState = _discoveryDataState.asStateFlow()

    private val _newsUiState = MutableStateFlow<UiState>(UiState.Nothing)
    val newsUiState = _newsUiState.asStateFlow()

    private val _stocksUiState = MutableStateFlow<UiState>(UiState.Nothing)
    val stocksUiState = _stocksUiState.asStateFlow()



    init {
        onEvent(DiscoveryEvent.FetchNews)
        onEvent(DiscoveryEvent.FetchStocks)
    }

    fun onEvent(event: DiscoveryEvent){

        when(event){
            DiscoveryEvent.FetchNews -> {
                _newsUiState.value = UiState.Processing
                viewModelScope.launch {
                    newsRepository.fetchAll {
                        when(it){
                            is RequestState.Failure -> _newsUiState.value = UiState.Failure(message = it.error!!)
                            RequestState.Processing -> _newsUiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _discoveryDataState.value = _discoveryDataState.value.copy(news = it.data!!)
                                _newsUiState.value = UiState.Success(message = it.message!!)
                            }
                        }
                    }
                }
            }
            DiscoveryEvent.FetchStocks -> {
                _stocksUiState.value = UiState.Processing
                viewModelScope.launch {
                    val stockModels: MutableList<StockModel> = mutableListOf()
                    listOfStocks.forEach { stock ->
                        stocksRepository.fetch(stock.abbreviation){
                            when(it){
                                is RequestState.Failure -> _stocksUiState.value = UiState.Failure(message = it.error!!)
                                RequestState.Processing -> _stocksUiState.value = UiState.Processing
                                is RequestState.Success -> stockModels.add(it.data!!)
                            }
                        }
                    }
                    _discoveryDataState.value = _discoveryDataState.value.copy(stocks = stockModels)
                    _stocksUiState.value = UiState.Success(message = "Success")
                }
            }
        }
    }
}