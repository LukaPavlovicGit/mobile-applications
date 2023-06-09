package com.example.raftrading.features.discovery


data class StockModel(
    val data: StockData
)

data class StockData(
    val ask: Double,
    val bid: Double,
    val chart: Chart,
    val close: Double,
    val currency: String,
    val instrumentId: String,
    val last: Double,
    val metrics: Metrics,
    val name: String,
    val open: Double,
    val symbol: String
)

data class Chart(
    val bars: List<Bar>
)

data class Metrics(
    val alpha: Double,
    val beta: Double,
    val ebit: Double,
    val eps: Double,
    val marketCup: Double,
    val sharp: Double
)

data class Bar(
    val price: Double,
    val time: String
)