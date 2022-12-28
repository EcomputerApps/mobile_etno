package com.example.mobile_etno.models

data class UserVillager(
    var username: String ? = null,
    var events: MutableList<Event> ? = mutableListOf(),
    var pharmacies: MutableList<Pharmacy> ? = mutableListOf(),
    var tourism: MutableList<Tourism> ? = mutableListOf(),
    var deaths: MutableList<Death> ? = mutableListOf()
)
