package com.example.mobile_etno.models

import java.util.UUID

data class Event(
    var idEvent: UUID ? = null,
    var username: String ? = null,
    var title: String ? = null,
    var address: String ? = null,
    var description: String ? = null,
    var organization: String ? = null,
    var reservePrice: Double ? = null,
    var seats: Int ? = null,
    var capacity: Int ? = null,
    var link: String ? = null,
    var imageUrl: String ? = null,
    var startDate: String ? = null,
    var endDate: String ? = null,
    var publicationDate: String ? = null,
    var time: String ? = null,
    var lat: String ? = null,
    var long: String ? = null,
    var images: MutableList<Image> ? = mutableListOf(),
    var videos: MutableList<Video> ? = mutableListOf(),
    var userSubscriptions: MutableList<UserSubscription> ? = mutableListOf()
)
