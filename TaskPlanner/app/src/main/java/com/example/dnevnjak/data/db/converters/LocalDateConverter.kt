package com.example.dnevnjak.data.db.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class LocalDateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDate {
        return Instant.ofEpochSecond(value).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
    }

}