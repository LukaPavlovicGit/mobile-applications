package com.example.raftrading.data

import com.example.raftrading.features.discovery.StockModel
import com.example.raftrading.states.RequestState

interface StocksRepository {

    suspend fun fetch(stock: String, result: (RequestState<StockModel>) -> Unit)
}