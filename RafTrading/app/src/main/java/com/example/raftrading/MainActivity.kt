package com.example.raftrading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.raftrading.application.SharedPreferencesManager
import com.example.raftrading.features.login.LoginFragment
import com.example.raftrading.features.registration.RegistrationFragment
import com.example.raftrading.presentation.MainFragment
import com.example.raftrading.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                val transaction = supportFragmentManager.beginTransaction()
                when (sharedPreferencesManager.isAuthenticated()){
                    true -> transaction.replace(R.id.activity_main_fragment_container, MainFragment(), Constants.MAIN_FRAGMENT_TAG)
                    false -> transaction.replace(R.id.activity_main_fragment_container, LoginFragment(), Constants.LOGIN_FRAGMENT_TAG)
                }
                transaction.commit()
                false
            }
        }

        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
    }
}
