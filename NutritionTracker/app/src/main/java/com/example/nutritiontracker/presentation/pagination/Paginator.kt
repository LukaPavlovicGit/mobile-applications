package com.example.nutritiontracker.presentation.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    suspend fun reset()
}