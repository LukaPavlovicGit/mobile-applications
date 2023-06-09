package com.example.dnevnjak.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.composable.LoginPage
import com.example.dnevnjak.utilities.Constants.Companion.MAIN_FRAGMENT_TAG

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                LoginPage( onSuccess = { onLoginSuccess() } )
            }
        }
    }

    private fun onLoginSuccess(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, MainFragment(), MAIN_FRAGMENT_TAG)
        transaction?.commit()
    }
}

