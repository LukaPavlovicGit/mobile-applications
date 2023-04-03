package com.example.dnevnjak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.dnevnjak.presentation.fragments.LoginFragment
import com.example.dnevnjak.utilities.Constants
import com.example.dnevnjak.utilities.Constants.Companion.LOGIN_FRAGMENT_TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {

                val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_PATH, MODE_PRIVATE)
                val isLoggedBefore = sharedPreferences.getString(Constants.USERNAME_KEY, null)
                val transaction = supportFragmentManager.beginTransaction()

                if(isLoggedBefore.isNullOrEmpty())
                    transaction.replace(R.id.activity_main_fragment_container, LoginFragment(), LOGIN_FRAGMENT_TAG)
                else{

                }
                transaction.commit()

                false
            }
        }

        setContentView(R.layout.activity_main)

    }

}



