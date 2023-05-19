package com.example.raftrading.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.raftrading.application.SharedPreferencesManager
import com.example.raftrading.utils.Constants
import com.squareup.moshi.Moshi
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

    @Provides
    fun sharedPref(@ApplicationContext context: Context):
            SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE)
}