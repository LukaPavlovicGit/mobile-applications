package com.example.dnevnjak.application

import android.app.Application
import com.example.dnevnjak.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DnevnjakApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = listOf(appModule)

        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@DnevnjakApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}