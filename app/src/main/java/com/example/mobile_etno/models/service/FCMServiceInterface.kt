package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.FCMToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMServiceInterface {

    @POST("FCMTokens")
    fun saveFcmToken(@Body fcmToken: FCMToken): Call<FCMToken>?
}