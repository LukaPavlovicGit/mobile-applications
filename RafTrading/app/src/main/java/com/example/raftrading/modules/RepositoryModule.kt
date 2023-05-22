package com.example.raftrading.modules

import com.example.raftrading.data.NewsRepository
import com.example.raftrading.data.StocksRepository
import com.example.raftrading.data.UserRepository
import com.example.raftrading.data.dataSource.remote.NewsService
import com.example.raftrading.data.dataSource.remote.StockService
import com.example.raftrading.data.firebaseDB.UserRepositoryFirebaseImpl
import com.example.raftrading.data.retrofit.NewsRepositoryImpl
import com.example.raftrading.data.retrofit.StocksRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        @Named("Users")  usersRoot: DatabaseReference
    ): UserRepository = UserRepositoryFirebaseImpl(auth, usersRoot)

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsService: NewsService
    ): NewsRepository = NewsRepositoryImpl(newsService)

    @Provides
    @Singleton
    fun provideStocksRepository(
        stocksService: StockService
    ): StocksRepository = StocksRepositoryImpl(stocksService)


}