package com.example.dnevnjak.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.composable.MainPage
import com.example.dnevnjak.utilities.Constants

class MainFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                MainPage(onLogout = { onLogout() })
            }
        }
    }

    private fun onLogout(){
        val transaction: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction();
        transaction.replace(R.id.activity_main_fragment_container, LoginFragment(),Constants.LOGIN_FRAGMENT_TAG)
        transaction.commit();
    }

}