package com.example.raftrading.data.dataSource.remote

import com.example.raftrading.features.discovery.StockModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StockService {

    @Headers("Accept: application/json")
    @GET("/rma/searchQuote")
    suspend fun get(@Query("symbol") stock: String): Response<StockModel>
}