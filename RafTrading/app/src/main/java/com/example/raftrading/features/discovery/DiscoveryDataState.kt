package com.example.raftrading.features.discovery

data class DiscoveryDataState(
    val news: NewsModel? = null,
    val stocks: MutableList<StockModel> = mutableListOf()
)
