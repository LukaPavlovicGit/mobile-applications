package com.example.dnevnjak.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dnevnjak.presentation.composable.MainPage
import com.example.dnevnjak.presentation.states.ObligationState
import com.example.dnevnjak.presentation.viewModels.ObligationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel: ObligationViewModel by viewModel()
                val state = viewModel.state.collectAsState()
                MainPage(state = state.value, onEvent = viewModel::onEvent)
            }
        }
    }

}