package com.example.nutritiontracker.presentation.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nutritiontracker.R
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.presentation.fragments.LoginFragment
import com.example.nutritiontracker.presentation.fragments.MainFragment
import com.example.nutritiontracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        setAppTheme()
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
    }

    private fun setAppTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkModeEnabled = currentNightMode == Configuration.UI_MODE_NIGHT_YES
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}