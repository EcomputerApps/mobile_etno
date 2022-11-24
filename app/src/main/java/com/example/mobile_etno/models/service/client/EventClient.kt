package com.example.mobile_etno.models.service.client

import com.example.mobile_etno.models.service.EventServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EventClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val eventService: EventServiceInterface = retrofit.create(EventServiceInterface::class.java)
}