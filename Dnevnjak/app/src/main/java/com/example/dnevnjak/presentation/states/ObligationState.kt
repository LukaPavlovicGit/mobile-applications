package com.example.dnevnjak.presentation.states

import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ObligationState(
    var id: Int = 0,
    var date: LocalDate = LocalDate.now(),
    var title: String = "",
    var start: LocalTime = LocalTime.now(),
    var end: LocalTime = LocalTime.now(),
    var description: String = "",
    var priority: Priority = Priority.Low,

    var obligationMode: String = "",
    var dateDiffFormatStr: String = "",

    var isReviewingObligation : Boolean = false,
    var isAddingObligation: Boolean = false,
    var isEditingObligation: Boolean = false,
    var isDeletingObligation : Boolean = false,
)