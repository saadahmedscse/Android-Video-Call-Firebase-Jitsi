package com.caffeine.videocall_jitsi.services.firebase

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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

        when (data["status"]) {
            "ringing" -> {
                val uid = data["uid"]
                val senderName = data["senderName"]
                val senderEmail = data["senderEmail"]
                val senderUid = data["senderUid"]
                val meetingId = data["meetingId"]
                if (uid == SessionManager.getInstance(applicationContext).uid) {
                    val i = Intent(this, IncomingCallActivity::class.java)
                    i.putExtra("uid", senderUid)
                    i.putExtra("email", senderEmail)
                    i.putExtra("name", senderName)
                    i.putExtra("meetingId", meetingId)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    this.startActivity(i)
                }
            }
            "accepted" -> {
                val i = Intent("ON_CALL_ACTION")
                i.putExtra("call_status", "accepted")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
            }
            "rejected" -> {
                val i = Intent("ON_CALL_ACTION")
                i.putExtra("call_status", "rejected")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
            }
            "hangup" -> {
                val i = Intent("ON_CALL_ACTION")
                i.putExtra("call_status", "hangup")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
            }
        }
    }
}