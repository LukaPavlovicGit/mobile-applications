package com.example.nutritiontracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.nutritiontracker.R
import com.example.nutritiontracker.presentation.composable.RegistrationScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.raftrading.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                RegistrationScreen( onRegisterSuccess = { onSuccess() }, login = { setLoginFragment() } )
            }
        }
    }

    private fun onSuccess(){
        toast(requireContext(), "Login now...")
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, LoginFragment(), Constants.LOGIN_FRAGMENT_TAG)
        transaction?.commit()
    }

    private fun setLoginFragment(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, LoginFragment(), Constants.LOGIN_FRAGMENT_TAG)
        transaction?.commit()
    }

}