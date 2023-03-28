package com.example.raftrading.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.raftrading.R
import com.example.raftrading.presentation.composable.LoginPage
import com.example.raftrading.utilities.Constants
import com.example.raftrading.presentation.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {

                val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                val isLoggedBefore = sharedPreferences.getString(Constants.USERNAME_KEY, null)

                if(isLoggedBefore.isNullOrEmpty())
                    setContent { LoginPage() }
                else{

                }

                false
            }
        }
        setContentView(R.layout.activity_main)

    }
}