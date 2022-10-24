package com.caffeine.videocall_jitsi.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

import com.caffeine.videocall_jitsi.services.model.api.MessageBody
import com.caffeine.videocall_jitsi.services.model.api.MessageResponse

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("send")
    fun sendRemoteMessage(@Header("Authorization") serverKey: String, @Body body: MessageBody): Call<MessageResponse>
}