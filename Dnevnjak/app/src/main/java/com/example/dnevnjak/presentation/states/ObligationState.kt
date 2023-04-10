package com.example.dnevnjak.presentation.states

import com.example.dnevnjak.utilities.Priority
import java.time.LocalDate
import java.time.LocalDateTime

data class ObligationState(
    var date: LocalDate = LocalDate.now(),
    var title: String = "",
    var start: LocalDateTime = LocalDateTime.now(),
    var end: LocalDateTime = LocalDateTime.now(),
    var description: String = "",
    var priority: Priority = Priority.Low,

    var dateDiffFormatStr: String = "",

    var isReviewingObligation : Boolean = false,
    var isAddingObligation: Boolean = false,
    var isEditingObligation: Boolean = false,
    var isDeletingObligation : Boolean = false,
)