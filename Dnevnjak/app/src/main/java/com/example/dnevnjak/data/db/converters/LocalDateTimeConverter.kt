package com.example.dnevnjak.data.db.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), TimeZone.getDefault().toZoneId())
    }

    @TypeConverter
    fun dateToTimestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
    }
}