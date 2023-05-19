package com.example.dnevnjak.utilities

fun String.isLongEnough() = length > 5
fun String.hasEnoughDigits() = any(Char::isDigit)
fun String.isMixedCase() = any(Char::isUpperCase)
fun String.hasSpecialChar() = none { it in "~#^|\$%&*!)" }

val requirements = listOf(String::isLongEnough, String::hasEnoughDigits, String::isMixedCase, String::hasSpecialChar)
val String.meetsRequirements get() = requirements.all { check -> check(this) }