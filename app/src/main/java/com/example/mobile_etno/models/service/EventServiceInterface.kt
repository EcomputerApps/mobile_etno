package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EventServiceInterface {
    //I have to use sqllite to save token to endpoint request ...

    @GET("events")
    fun getEvent(): Call<List<Event>>
}