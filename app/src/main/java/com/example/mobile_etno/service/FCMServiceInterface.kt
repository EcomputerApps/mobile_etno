package com.example.mobile_etno.service

import com.example.mobile_etno.models.FCMToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FCMServiceInterface {
    @POST("FCMTokens")
    fun saveFcmToken(@Body fcmToken: FCMToken): Call<FCMToken>?
}