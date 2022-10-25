package com.caffeine.videocall_jitsi.view.calling

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.caffeine.videocall_jitsi.api.RetroClient
import com.caffeine.videocall_jitsi.databinding.ActivityOutgoingCallBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.services.model.api.Data
import com.caffeine.videocall_jitsi.services.model.api.MessageBody
import com.caffeine.videocall_jitsi.services.model.api.MessageResponse
import com.caffeine.videocall_jitsi.services.model.common.User
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.utils.Constants.Api.FCM_KEY
import com.saadahmedsoft.tinydb.TinyDB
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.util.UUID

class OutgoingCallActivity : BaseActivity<ActivityOutgoingCallBinding>(ActivityOutgoingCallBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding?
        get() = null

    private var uid = ""
    private var name = ""
    private var email = ""
    private var meetingId = ""

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        uid = intent.getStringExtra("uid")!!
        name = intent.getStringExtra("name")!!
        email = intent.getStringExtra("email")!!

        meetingId = uid + "_" + UUID.randomUUID().toString().substring(0, 7)

        binding.item = User(
            uid,
            name,
            email
        )

        binding.btnDisconnect.onClicked {
            onHangUpButtonClicked()
        }

        sendRemoteMessage("ringing")
    }

    override fun observeData() {}

    private fun onHangUpButtonClicked() {
        meetingId = ""
        sendRemoteMessage("hangup")
        onBackButtonPressed()
    }

    private fun sendRemoteMessage(status: String) {
        val myProfile = TinyDB.getInstance(this).getObject<User>("my_profile", User::class.java)
        val data = Data(
            status,
            uid,
            myProfile.name!!,
            myProfile.email!!,
            myProfile.uid!!,
            meetingId
        )

        val body = MessageBody("/topics/topseba", data)

        val call = RetroClient.getInstance.sendRemoteMessage(FCM_KEY, body)
        call.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    binding.tvCalling.text = "Calling"
                } else {
                    longToast(response.message())
                    onBackButtonPressed()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                longToast(t.message!!)
                onBackButtonPressed()
            }
        })
    }

    private fun initiateMeeting() {
        val userInfo = Bundle()
        userInfo.putString("displayName", name)
        userInfo.putString("email", email)

        val options = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://meet.jit.si"))
            .setRoom(meetingId)
            .setAudioMuted(false)
            .setVideoMuted(false)
            .setAudioOnly(false)
            .setUserInfo(JitsiMeetUserInfo(userInfo))
            .setConfigOverride("requireDisplayName", true)
            .build()

        JitsiMeetActivity.launch(applicationContext, options)
        finish()
    }

    private val callStatusListener = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent?.getStringExtra("call_status")
            if (type != null) {
                if (type == "rejected") {
                    onBackButtonPressed()
                    longToast("Call Rejected")
                }
                else if (type == "accepted") {
                    initiateMeeting()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            callStatusListener,
            IntentFilter("ON_CALL_ACTION")
        )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(
            callStatusListener
        )
    }
}