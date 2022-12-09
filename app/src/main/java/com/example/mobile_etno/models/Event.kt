package com.example.mobile_etno.models

import java.util.*

data class Event(
    var idEvent: String? = null,
    var title: String? = null,
    var address: String? = null,
    var description: String? = null,
    var organization: String? = null,
    var link: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var publicationDate: String? = null,
    var time: String? = null,
    var lat: String? = null,
    var long: String? = null,
    var subscription: Boolean? = null,
    var images: MutableList<Image>?= mutableListOf(),
    var videos: MutableList<Video>? = mutableListOf()
)
