package com.example.mobile_etno.models.service.client

import com.example.mobile_etno.models.service.UserVillagerInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserVillagerClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userVillager: UserVillagerInterface = retrofit.create(UserVillagerInterface::class.java)
}