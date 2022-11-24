package com.example.mobile_etno.models

import java.util.*

data class Event(
    var idEvent: UUID? = null,
    var title: String? = null,
    var description: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var publicationDate: Date? = null,
    var lat: String? = null,
    var long: String? = null,
    var subscription: Boolean? = null,
    var images: MutableList<Image>? = mutableListOf(),
    var videos: MutableList<Video>? = mutableListOf()
)
