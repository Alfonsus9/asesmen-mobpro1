package com.alfonsus0031.dompetku.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertMillisToDateString(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}