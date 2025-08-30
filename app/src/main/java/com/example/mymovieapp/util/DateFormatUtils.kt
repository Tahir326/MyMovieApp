package com.example.mymovieapp.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun yearToDisplayDate(year: Int): String {
    return try {
        LocalDate.of(year, 1, 1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        "01/01/$year"
    }
}
