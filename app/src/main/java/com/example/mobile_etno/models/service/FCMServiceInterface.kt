package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.SectionSubscribe
import com.example.mobile_etno.models.Subscription
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FCMServiceInterface {
    @POST("FCMTokens")
    fun saveFcmToken(@Body fcmToken: FCMToken): Call<FCMToken>?

    @PUT("FCMTokens/section")
    fun addSectionToFCMToken(@Query("token") token: String,
                             @Query("name") name: String,
                             @Query("mail") mail: String,
                             @Query("phone") phone: String,
                             @Query("wallet") wallet: Double,
                             @Body sectionSubscribe: SectionSubscribe
                             ): Call<SectionSubscribe>

    @PUT("FCMTokens/dropout/section")
    fun dropOutSectionByTokenAndTitle(@Query("token") token: String,
                                      @Query("category") category: String,
                                      @Query("title") title: String): Call<SectionSubscribe>

}