package com.example.raftrading.features.discovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoveryFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DiscoveryScreen(
                    onNewsClicked = { url -> onNewsClicked(url) },
                    onStockClicked = { stockModel -> onStockClicked(stockModel) }
                )
            }
        }
    }

    private fun onNewsClicked(url :String?){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun onStockClicked(stockModel: StockModel){

    }

}