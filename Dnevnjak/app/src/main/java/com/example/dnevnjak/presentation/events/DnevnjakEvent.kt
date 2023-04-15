package com.example.dnevnjak.presentation.events

import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalTime

sealed interface DnevnjakEvent {

    data class SetTitle(val title: String): DnevnjakEvent

    data class SetStart(val start: LocalTime): DnevnjakEvent

    data class SetEnd(val end: LocalTime): DnevnjakEvent

    data class SetDescription(val description: String): DnevnjakEvent

    data class SetPriority(val priority: Priority): DnevnjakEvent

    object ShowPastObligations: DnevnjakEvent

    data class SetHeaderDate(val localDate: LocalDate): DnevnjakEvent

    object ShowAll: DnevnjakEvent

    data class SetSearchQuery(val query: String): DnevnjakEvent

    data class FilterByPriority(val priority: Priority): DnevnjakEvent

    data class DateTouched(val localDate: LocalDate): DnevnjakEvent

    data class SelectedObligation(val obligationEntity: ObligationEntity): DnevnjakEvent

    object AddObligation: DnevnjakEvent

    object EditObligation: DnevnjakEvent

    object SaveObligation: DnevnjakEvent

    object CreateObligation: DnevnjakEvent

    object DeleteObligation: DnevnjakEvent

    object DeleteObligationConfirmed: DnevnjakEvent

    object DeleteObligationCanceled: DnevnjakEvent

    object CancelObligation: DnevnjakEvent
}