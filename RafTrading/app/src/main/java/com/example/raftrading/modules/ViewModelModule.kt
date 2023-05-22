package com.example.raftrading.modules

import com.example.raftrading.application.SharedPreferencesManager
import com.example.raftrading.data.NewsRepository
import com.example.raftrading.data.StocksRepository
import com.example.raftrading.data.UserRepository
import com.example.raftrading.features.discovery.DiscoveryViewModel
import com.example.raftrading.features.login.LoginViewModel
import com.example.raftrading.features.registration.RegistrationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideRegistrationViewModel(
        userRepository: UserRepository,
        sharedPrefManager: SharedPreferencesManager
    ): RegistrationViewModel = RegistrationViewModel(userRepository, sharedPrefManager)

    @Singleton
    @Provides
    fun provideLoginViewModel(
        userRepository: UserRepository,
        sharedPrefManager: SharedPreferencesManager
    ): LoginViewModel = LoginViewModel(userRepository, sharedPrefManager)

    @Singleton
    @Provides
    fun provideDiscoveryViewModel(
        newsRepository: NewsRepository,
        stocksRepository: StocksRepository,
    ): DiscoveryViewModel = DiscoveryViewModel(newsRepository, stocksRepository)

}