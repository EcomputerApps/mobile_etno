package com.example.mobile_etno.utils

object StringSpace {
    fun padRight(s: String?,  n: Int): String {
        val number = s.toString().length + n
        return "%-${number}s".format(s)
    }
}