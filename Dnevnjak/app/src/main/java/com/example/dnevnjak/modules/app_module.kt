package com.example.dnevnjak.modules

import androidx.room.Room
import com.example.dnevnjak.data.db.Database
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.data.repository.impl.ObligationRepositoryImpl
import com.example.dnevnjak.presentation.viewModels.LoginVIewModel
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val appModule = module {

//    single<SharedPreferences> {
//        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
//    }

    single {
        Room
        .databaseBuilder(androidContext(), Database::class.java, "Dnevnjak")
        .fallbackToDestructiveMigration().createFromAsset("database/Dnevnjak.db")
        .build()
    }

    single<ObligationRepository> { ObligationRepositoryImpl(get()) }

    single { createMoshi() }


    single { get<Database>().getObligationDao() }

    viewModel { LoginVIewModel() }

    viewModel { MainViewModel(get()) }

}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}