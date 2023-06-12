package com.example.nutritiontracker.modules

import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.viewModel.LoginViewModel
import com.example.nutritiontracker.viewModel.RegistrationViewModel
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
    fun provideLoginViewModel(
        userRepository: AuthRepository,
        sharedPrefManager: SharedPreferencesManager
    ): LoginViewModel = LoginViewModel(userRepository, sharedPrefManager)

    @Singleton
    @Provides
    fun provideRegistrationViewModel(
        userRepository: AuthRepository,
        sharedPrefManager: SharedPreferencesManager
    ): RegistrationViewModel = RegistrationViewModel(userRepository, sharedPrefManager)
}