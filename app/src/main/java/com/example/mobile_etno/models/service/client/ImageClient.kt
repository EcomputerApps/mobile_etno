package com.example.mobile_etno.models.service.client

import com.example.mobile_etno.models.service.ImageServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImageClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val imageService: ImageServiceInterface = retrofit.create(ImageServiceInterface::class.java)
}