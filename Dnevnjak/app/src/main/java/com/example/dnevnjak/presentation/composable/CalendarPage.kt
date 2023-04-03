package com.example.dnevnjak.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.composable.ui.theme.BLACK
import com.example.dnevnjak.presentation.composable.ui.theme.PURPLE700
import com.example.dnevnjak.presentation.composable.ui.theme.TEAL200

@Composable
fun CalendarPage() {

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        content = {
            items(100) { i ->
                Box(
                    modifier = Modifier
                        .height(113.dp)
                        .border(2.dp, Color.Black)
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Item $i",
                         fontSize = 14.sp)
                }
            }
        }
    )
}