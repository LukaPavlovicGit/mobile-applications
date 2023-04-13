package com.example.dnevnjak.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.composable.LoginPage
import com.example.dnevnjak.utilities.Constants.Companion.EMAIL_KEY
import com.example.dnevnjak.utilities.Constants.Companion.MAIN_FRAGMENT_TAG
import com.example.dnevnjak.utilities.Constants.Companion.SHARED_PREFERENCES_PATH
import com.example.dnevnjak.utilities.Constants.Companion.USERNAME_KEY

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                LoginPage(
                    onSuccess = { username, email -> onLoginSuccess(username, email) }
                )
            }
        }
    }

    private fun onLoginSuccess(username: String, email: String){

        Log.e("TAG", "$username $email")

        val sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(EMAIL_KEY,  email)
            putString(USERNAME_KEY, username)
            apply()
        }

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, MainFragment(), MAIN_FRAGMENT_TAG)
        transaction?.commit()
    }
}

