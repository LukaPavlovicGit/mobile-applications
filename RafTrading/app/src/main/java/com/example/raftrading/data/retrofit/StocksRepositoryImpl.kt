package com.example.raftrading.data.retrofit

import com.example.raftrading.data.StocksRepository
import com.example.raftrading.data.dataSource.remote.StockService
import com.example.raftrading.features.discovery.StockModel
import com.example.raftrading.states.RequestState

class StocksRepositoryImpl (
    private val stockService: StockService
): StocksRepository {

    override suspend fun fetch(stock: String, result: (RequestState<StockModel>) -> Unit) {
        val ans = stockService.get(stock)

        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "Stock fetched successfully..."))
        }
        else{
            result.invoke(RequestState.Failure(error = "Something went wrong.. Stock is NOT fetched!"))
        }

    }

}