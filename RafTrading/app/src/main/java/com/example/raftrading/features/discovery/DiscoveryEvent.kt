package com.example.raftrading.features.discovery

interface DiscoveryEvent {
    object FetchNews: DiscoveryEvent
    object FetchStocks: DiscoveryEvent
    object ResetUiState: DiscoveryEvent
}