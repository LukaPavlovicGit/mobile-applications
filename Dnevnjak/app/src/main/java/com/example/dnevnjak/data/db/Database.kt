package com.example.dnevnjak.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dnevnjak.data.db.converters.LocalDateConverter
import com.example.dnevnjak.data.db.converters.LocalTimeConverter
import com.example.dnevnjak.data.db.converters.StringListConverter
import com.example.dnevnjak.data.db.daos.ObligationDao
import com.example.dnevnjak.data.models.ObligationEntity

@Database(
    entities = [ObligationEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class, StringListConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun getObligationDao(): ObligationDao
}