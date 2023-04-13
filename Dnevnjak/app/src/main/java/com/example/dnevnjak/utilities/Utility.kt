package com.example.dnevnjak.utilities

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Utility {
    companion object{

        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        private val formatter = DateTimeFormatter.ofPattern("HH:mm")

        fun fullDateFormatterStr(date: LocalDate): String {
            val localDate = date.format(dateFormatter)
            val tokens = localDate.toString().split("-")
            return tokens[1] + " " + tokens[0] + ". " + tokens[2] + "."
        }

        fun dateFormatterStr(date: LocalDate): String {
            val localDate = date.format(dateFormatter)
            val tokens = localDate.toString().split("-")
            return tokens[1] + " " + tokens[2] + "."
        }

        fun timeFormatterStr(time: LocalTime): String{
            return time.format(formatter)
        }

    }
}