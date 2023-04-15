package com.example.dnevnjak.presentation.states

import com.example.dnevnjak.data.models.ObligationEntity
import java.time.LocalDate

data class DailyPlanState(
    val date: LocalDate = LocalDate.now(),
    val obligations: List<ObligationEntity> = emptyList(),
    val searchText: String = "",
    val isSearching: Boolean = false,
    val showPastObligations: Boolean = true,

)
