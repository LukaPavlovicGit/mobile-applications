package com.example.dnevnjak.presentation.states

import androidx.compose.ui.graphics.Color
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.collections.HashMap

data class ObligationState(
    var obligations: List<ObligationEntity> = emptyList(),
    var obligationsByDay: List<ObligationEntity> = emptyList(),
    var dayColorMap: HashMap<LocalDate, Color> = HashMap(),
    var title: String = "",
    var start: LocalDateTime = LocalDateTime.now(),
    var end: LocalDateTime = LocalDateTime.now(),
    var description: String = "",
    var priority: Priority = Priority.Low,
    var isAdding: Boolean = false,
    var isEditing: Boolean = false,
    var isDeleting: Boolean = false,
    var searchQuery: String = "",
    var filterPriority: Priority? = null,
    var showPastObligations: Boolean = true,
    var selectedDate: Long = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
    var headerDate: String = LocalDate.now().toString(),
    var headerFullDate: String = LocalDate.now().toString()
)