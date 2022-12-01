package com.example.mobile_etno.models.service.client

import com.example.mobile_etno.models.service.FCMServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FCMClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val FCMService: FCMServiceInterface = retrofit.create(FCMServiceInterface::class.java)
}