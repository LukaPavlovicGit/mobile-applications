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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideLoginViewModel(
        userRepository: AuthRepository,
        sharedPrefManager: SharedPreferencesManager
    ): LoginViewModel = LoginViewModel(userRepository, sharedPrefManager)

    @Provides
    @ViewModelScoped
    fun provideRegistrationViewModel(
        userRepository: AuthRepository,
        sharedPrefManager: SharedPreferencesManager
    ): RegistrationViewModel = RegistrationViewModel(userRepository, sharedPrefManager)

    @Provides
    @ViewModelScoped
    fun provideMainViewModel(
        mealRepository: MealRepository
    ): MainViewModel = MainViewModel(mealRepository)

    @Provides
    @ViewModelScoped
    fun provideMenuViewModel(
        mealRepository: MealRepository
    ): MenuViewModel = MenuViewModel(mealRepository)

    @Provides
    @ViewModelScoped
    fun provideMealViewModel(
        mealRepository: MealRepository
    ): MealViewModel = MealViewModel(mealRepository)

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(
        mealRepository: MealRepository
    ): ProfileViewModel = ProfileViewModel(mealRepository)
}