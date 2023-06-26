package com.example.nutritiontracker.modules

import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import com.example.nutritiontracker.data.datasource.remote.MealService
import com.example.nutritiontracker.data.datasource.remote.NutritionService
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.data.repositories.MealRepository
import com.example.nutritiontracker.data.repositories.NutritionRepository
import com.example.nutritiontracker.data.repositories.impl.AuthRepositoryImpl
import com.example.nutritiontracker.data.repositories.impl.MealRepositoryImpl
import com.example.nutritiontracker.data.repositories.impl.NutritionRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth);

    @Provides
    @Singleton
    fun provideMealRepository(
        mealService: MealService,
        mealDao: MealDao
    ): MealRepository = MealRepositoryImpl(mealService, mealDao)

    @Provides
    @Singleton
    fun provideNutritionRepository(
        nutritionService: NutritionService
    ): NutritionRepository = NutritionRepositoryImpl(nutritionService)

}