package com.example.dnevnjak.presentation.states

import com.example.dnevnjak.data.models.priorityEnum.Priority
import java.time.LocalDate
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

    var isReviewing: Boolean = false,
    var isAdding: Boolean = false,
    var isDeleting: Boolean = false,
    var isEditing: Boolean = false,

    var isSingleObligationMode: Boolean = false,

    var timeOverlap: Boolean = false
)