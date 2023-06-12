package com.example.nutritiontracker.passwordValidation

fun String.isLongEnough() = length > 5
fun String.hasEnoughDigits() = any(Char::isDigit)
fun String.isMixedCase() = any(Char::isUpperCase)
fun String.hasSpecialChar() = none { it in "~#^|\$%&*!)" }

val requirements = listOf(String::isLongEnough, String::hasEnoughDigits, String::isMixedCase, String::hasSpecialChar)
val String.isPasswordValid get() = requirements.all { check -> check(this) }