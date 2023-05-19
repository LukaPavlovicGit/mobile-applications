package com.example.raftrading.data.dataSource.remote

import com.example.raftrading.dtos.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {
    @Headers("Accept: application/json")
    @GET("getNews")
    suspend fun getAll(): Response<Root>
}