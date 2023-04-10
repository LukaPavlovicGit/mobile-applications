package com.example.dnevnjak.presentation.events

import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalDateTime

sealed interface ObligationEvent {
    object SaveObligation: ObligationEvent

    object EditObligation: ObligationEvent

    data class DeleteObligation(val obligation: ObligationEntity): ObligationEvent

    data class SetTitle(val title: String): ObligationEvent

    data class SetStart(val start: LocalDateTime): ObligationEvent

    data class SetEnd(val end: LocalDateTime): ObligationEvent

    data class SetDescription(val description: String): ObligationEvent

    data class SetPriority(val priority: Priority): ObligationEvent

    object ShowNewObligationScreen: ObligationEvent

    object HideDialog: ObligationEvent

    object ShowConfirmationDialog: ObligationEvent

    object HideConfirmationDialog: ObligationEvent

    object ShowPastObligations: ObligationEvent

    data class SetHeaderDate(val localDate: LocalDate): ObligationEvent

    data class SetHeaderFullDate(val localDate: LocalDate): ObligationEvent

    data class FilterObligations(val filterPriority: Priority): ObligationEvent

    object ShowAll: ObligationEvent

    data class SetSearchQuery(val query: String): ObligationEvent

    data class FilterByPriority(val priority: Priority): ObligationEvent

    data class DateTouched(val localDate: LocalDate): ObligationEvent

    data class SelectedObligation(val obligationEntity: ObligationEntity): ObligationEvent

}