package com.example.raftrading.features.discovery

val listOfStocks = listOf<Stock>(
    Stock("TESLA", "TSLA"),
    Stock("NVIDIA", "NVDA"),
    Stock("APPLE", "AAPL"),
    Stock("MICROSOFT", "MSFT"),
    Stock("AMD", "AMD"),
    Stock("AMAZON", "AMZN"),
    Stock("META", "META"),
    Stock("ALPHABET_A", "GOOGL"),
    Stock("ALPHABET_C", "GOOG"),
    Stock("BERKSHIRE", "BRKa"),
    Stock("NETFLIX", "NFLX"),
    Stock("DEERE", "DE"),
    Stock("ALIBABA", "BABA")
)

data class Stock(
    val name: String = "",
    val abbreviation: String = ""
)
