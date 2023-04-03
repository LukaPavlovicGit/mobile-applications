package com.example.dnevnjak.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dnevnjak.presentation.composable.LoginPage
import com.example.dnevnjak.presentation.composable.MainPage

class MainFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return return ComposeView(requireContext()).apply {
            setContent {
                MainPage()
            }
        }
    }

}