package com.example.nutritiontracker.data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalTime
import java.util.*

class LocalTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): LocalTime {
        return LocalTime.ofInstant(Instant.ofEpochSecond(value), TimeZone.getDefault().toZoneId())
    }

    @TypeConverter
    fun dateToTimestamp(localTime: LocalTime): Long {
        return localTime.toSecondOfDay().toLong()
    }
}