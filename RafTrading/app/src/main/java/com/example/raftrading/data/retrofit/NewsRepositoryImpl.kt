package com.example.raftrading.data.retrofit

import com.example.raftrading.data.NewsRepository
import com.example.raftrading.data.dataSource.remote.NewsService
import com.example.raftrading.features.discovery.NewsModel
import com.example.raftrading.states.RequestState

class NewsRepositoryImpl (
    private val newsService: NewsService
): NewsRepository {

    override suspend fun fetchAll(result: (RequestState<NewsModel>) -> Unit) {
        val ans = newsService.getAll()

        if(ans.isSuccessful){
            result.invoke(RequestState.Success(data = ans.body(), message = "News fetched successfully..."))
        }
        else{
            result.invoke(RequestState.Failure(error = "Something went wrong.. News are NOT fetched!"))
        }

    }

}