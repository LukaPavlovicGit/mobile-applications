package com.example.nutritiontracker.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.raftrading.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // dependencies will live as long as application
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(sharedPref: SharedPreferences):
            SharedPreferencesManager = SharedPreferencesManager(sharedPref)

    @Singleton
    @Provides
    fun sharedPref(@ApplicationContext context: Context):
            SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideGson() = Gson()

}