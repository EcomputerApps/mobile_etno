package com.example.mobile_etno.utils

import java.time.format.DateTimeFormatter

object Parse {
    fun getDay(date: String): String {
        val dayAndMouth = date.substringAfter("-")
        val day = date.substringAfter("-").substringBefore("-")
        val mouth = dayAndMouth.substringAfter("-")
        val year = date.substringBefore("-")

        return if (!day.contains("0") && day.toInt() < 10) "0$day" else day
    }

    fun getMouth(date: String): String {
        val dayAndMouth = date.substringAfter("-")
        val mouth = dayAndMouth.substringAfter("-")
        val year = date.substringBefore("-")

        return if (!mouth.contains("0") && mouth.toInt() < 10) "0$mouth" else mouth
    }

    fun getYear(date: String): String {
        return date.substringBefore("-")
    }

    fun formatEuropean(date: String): String {
        val europeanDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return europeanDateFormatter.format(java.time.LocalDate.of(getYear(date).toInt(), getMouth(date).toInt(), getDay(date).toInt()))
    }
}