package com.example.raftrading.data

import com.example.raftrading.dtos.Root
import com.example.raftrading.states.RequestState

interface NewsRepository {

    suspend fun fetchAll(result: (RequestState<Root>) -> Unit)
}