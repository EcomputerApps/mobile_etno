package com.example.mobile_etno.service.client

import com.example.mobile_etno.service.SubscriptionServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SubscriptionClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val subscriptionService: SubscriptionServiceInterface = retrofit.create(
        SubscriptionServiceInterface::class.java)
}