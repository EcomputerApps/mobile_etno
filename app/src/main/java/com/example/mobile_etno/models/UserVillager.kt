package com.example.mobile_etno.models

import com.example.mobile_etno.models.news.New
import com.example.mobile_etno.models.phone.Phone

data class UserVillager(
    var username: String ? = null,
    var events: MutableList<Event> ? = mutableListOf(),
    var pharmacies: MutableList<Pharmacy> ? = mutableListOf(),
    var tourism: MutableList<Tourism> ? = mutableListOf(),
    var deaths: MutableList<Death> ? = mutableListOf(),
    var phones: MutableList<Phone> ? = mutableListOf(),
    var news: MutableList<New> ? = mutableListOf()
)