package com.example.nutritiontracker.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.nutritiontracker.presentation.composable.CategoriesScreen
import com.example.nutritiontracker.presentation.composable.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CategoriesScreen()
            }
        }
    }
}