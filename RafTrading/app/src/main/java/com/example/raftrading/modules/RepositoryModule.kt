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
<<<<<<< HEAD
import com.google.gson.Gson
=======
>>>>>>> 91a31dcc44b48c7e53ab742c1fa67cd723b87c29
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
<<<<<<< HEAD
        @Named("Users")  usersRoot: DatabaseReference,
        gson: Gson
    ): UserRepository = UserRepositoryFirebaseImpl(auth, usersRoot, gson)
=======
        @Named("Users")  usersRoot: DatabaseReference
    ): UserRepository = UserRepositoryFirebaseImpl(auth, usersRoot)
>>>>>>> 91a31dcc44b48c7e53ab742c1fa67cd723b87c29

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