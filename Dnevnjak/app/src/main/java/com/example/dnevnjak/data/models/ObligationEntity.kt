package com.example.dnevnjak.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dnevnjak.data.models.priorityEnum.Priority
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "obligations")
data class ObligationEntity (
    var date: LocalDate = LocalDate.now(),
    var priority: Priority = Priority.Low,
    var title: String = "",
    var description: String = "",
    var start: LocalTime = LocalTime.now(),
    var end: LocalTime = LocalTime.now(),
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
