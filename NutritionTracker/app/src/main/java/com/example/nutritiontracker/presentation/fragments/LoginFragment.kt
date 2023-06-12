package com.example.nutritiontracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.nutritiontracker.R
import com.example.nutritiontracker.presentation.composable.LoginScreen
import com.example.raftrading.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginScreen( onLoginSuccess = { setMainFragment() }, register = { setRegisterFragment() } )
            }
        }
    }

    private fun setMainFragment(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, MainFragment(), Constants.MAIN_FRAGMENT_TAG)
        transaction?.commit()
    }

    private fun setRegisterFragment(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, RegistrationFragment(), Constants.REGISTRATION_FRAGMENT_TAG)
        transaction?.commit()
    }
}