package com.example.dnevnjak.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ObligationReviewPage (
    viewModel: MainViewModel
){
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val obligation = remember { viewModel.selectedObligation.value }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = obligation.dateDiffFormatStr,
            fontSize = 35.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(10.dp)
                .clickable { viewModel.onEvent(ObligationEvent.HideDialog) }
        )

    }
}