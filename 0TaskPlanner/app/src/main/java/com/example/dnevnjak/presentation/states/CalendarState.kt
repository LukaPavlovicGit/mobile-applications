package com.example.dnevnjak.presentation.states

import androidx.compose.ui.graphics.Color
import com.example.dnevnjak.data.models.ObligationEntity
import java.time.LocalDate

data class CalendarState(
    val obligations: List<ObligationEntity> = emptyList(),
    val colors: Map<LocalDate, Color> = hashMapOf(),
    val displayedYear: Int = LocalDate.now().year,
    val displayedMonth: Int = LocalDate.now().monthValue,
    val headerDate: LocalDate = LocalDate.now()
)
