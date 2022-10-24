package com.caffeine.videocall_jitsi.services.firebase

import android.content.Intent
import android.util.Log
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.calling.IncomingCallActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        val uid = data["uid"]
        val senderName = data["senderName"]
        val senderEmail = data["senderEmail"]
        val senderUid = data["senderUid"]

        if (uid == SessionManager.getInstance(applicationContext).uid) {
            val i = Intent(applicationContext, IncomingCallActivity::class.java)
            i.putExtra("uid", senderUid)
            i.putExtra("email", senderEmail)
            i.putExtra("name", senderName)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
    }
}