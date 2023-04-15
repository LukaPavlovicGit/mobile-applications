package com.example.dnevnjak.presentation.events

import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

sealed interface ObligationEvent {

    data class SetTitle(val title: String): ObligationEvent

    data class SetStart(val start: LocalTime): ObligationEvent

    data class SetEnd(val end: LocalTime): ObligationEvent

    data class SetDescription(val description: String): ObligationEvent

    data class SetPriority(val priority: Priority): ObligationEvent

    object ShowPastObligations: ObligationEvent

    data class SetHeaderDate(val localDate: LocalDate): ObligationEvent

    object ShowAll: ObligationEvent

    data class SetSearchQuery(val query: String): ObligationEvent

    data class FilterByPriority(val priority: Priority): ObligationEvent

    data class DateTouched(val localDate: LocalDate): ObligationEvent

    data class SelectedObligation(val obligationEntity: ObligationEntity): ObligationEvent

    object AddObligation: ObligationEvent

    object EditObligation: ObligationEvent

    object SaveObligation: ObligationEvent

    object CreateObligation: ObligationEvent

    object DeleteObligation: ObligationEvent

    object DeleteObligationConfirmed: ObligationEvent

    object DeleteObligationCanceled: ObligationEvent

    object CancelObligation: ObligationEvent
}