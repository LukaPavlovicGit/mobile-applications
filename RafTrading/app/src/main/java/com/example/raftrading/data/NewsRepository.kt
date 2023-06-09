package com.example.raftrading.data

import com.example.raftrading.features.discovery.NewsModel
import com.example.raftrading.states.RequestState

interface NewsRepository {

    suspend fun fetchAll(result: (RequestState<NewsModel>) -> Unit)
}