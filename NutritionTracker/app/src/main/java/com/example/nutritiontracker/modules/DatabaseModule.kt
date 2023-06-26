package com.example.nutritiontracker.modules

import android.content.Context
import androidx.room.Room
import com.example.nutritiontracker.data.datasource.local.Database
import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "nutrition_tracker"
        ).createFromAsset("database/nutrition_tracker.db").build()


    @Provides
    @Singleton
    fun provideDao(database: Database): MealDao = database.getMealDao()

}