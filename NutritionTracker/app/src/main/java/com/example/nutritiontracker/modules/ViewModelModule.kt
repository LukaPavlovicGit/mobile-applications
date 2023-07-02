package com.example.nutritiontracker.modules

import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.viewModel.MainViewModel
import com.example.nutritiontracker.viewModel.LoginViewModel
import com.example.nutritiontracker.viewModel.MealViewModel
import com.example.nutritiontracker.viewModel.MenuViewModel
import com.example.nutritiontracker.viewModel.ProfileViewModel
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

    @Singleton
    @Provides
    fun provideMainViewModel(
        mealRepository: MealRepository
    ): MainViewModel = MainViewModel(mealRepository)

    @Singleton
    @Provides
    fun provideMenuViewModel(
        mealRepository: MealRepository
    ): MenuViewModel = MenuViewModel(mealRepository)

    @Singleton
    @Provides
    fun provideMealViewModel(
        mealRepository: MealRepository
    ): MealViewModel = MealViewModel(mealRepository)

    @Singleton
    @Provides
    fun provideProfileViewModel(
        mealRepository: MealRepository
    ): ProfileViewModel = ProfileViewModel(mealRepository)
}