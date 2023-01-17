package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.IncidentModel
import com.example.mobile_etno.models.UserVillager
import com.example.mobile_etno.models.mail.Mail
import com.example.mobile_etno.models.mail.MailSuccess
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserVillagerInterface {
    @GET("users/villagers")
    fun getUserByUsernameToVillager(@Query("username") username: String): Call<UserVillager>

    @GET("events")
    fun getEventByUsernameAndTitle(@Query("username") username: String, @Query("title") title: String): Call<Event>

    @POST("users/add/incident")
    fun addIncidentInUser(
        @Query("username") username: String,
        @Body incidentModel: IncidentModel
    ): Call<IncidentModel>

    @GET("incidents/villager")
    fun getIncidentsByUsernameAndFcmTokenAndTitle(
        @Query("username") username: String,
        @Query("fcmToken") fcmToken: String
    ): Call<List<IncidentModel>>

    @POST("sendMail")
    fun sendMailWithAttachment(
        @Body mail: Mail
    ): Call<MailSuccess>


}