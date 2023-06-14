package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

@Composable
fun LoadImageFromUrl(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "description" // Set a meaningful description if needed
    )
}