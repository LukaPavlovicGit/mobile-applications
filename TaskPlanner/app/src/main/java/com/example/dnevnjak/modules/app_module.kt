package com.example.dnevnjak.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.dnevnjak.data.db.Database
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.data.repository.UserRepository
import com.example.dnevnjak.data.repository.impl.ObligationRepositoryImpl
import com.example.dnevnjak.data.repository.impl.UserRepositoryImpl
import com.example.dnevnjak.presentation.viewModels.UserViewModel
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import com.example.dnevnjak.utilities.Constants.Companion.SHARED_PREFERENCES_PATH
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val appModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE)
    }

    single {
        Room
        .databaseBuilder(androidContext(), Database::class.java, "Dnevnjak")
        .fallbackToDestructiveMigration().createFromAsset("database/Dnevnjak.db")
        .build()
    }


    single<ObligationRepository> { ObligationRepositoryImpl(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { createMoshi() }

    single { get<Database>().getObligationDao() }

    single { get<Database>().getUserDao() }

    viewModel { UserViewModel(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}