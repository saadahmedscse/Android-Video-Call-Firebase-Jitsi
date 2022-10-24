package com.caffeine.videocall_jitsi.services.model.api

data class MessageBody(
    val to: String = "",
    val data: Data? = null,
    val notification: Data? = null
)
