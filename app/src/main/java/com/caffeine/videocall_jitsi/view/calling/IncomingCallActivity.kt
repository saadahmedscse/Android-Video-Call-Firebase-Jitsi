package com.caffeine.videocall_jitsi.view.calling

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.WindowManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.caffeine.videocall_jitsi.api.RetroClient
import com.caffeine.videocall_jitsi.databinding.ActivityIncomingCallBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.services.model.api.Data
import com.caffeine.videocall_jitsi.services.model.api.MessageBody
import com.caffeine.videocall_jitsi.services.model.api.MessageResponse
import com.caffeine.videocall_jitsi.services.model.common.User
import com.caffeine.videocall_jitsi.utils.RingtonePlayer
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.delay
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.utils.Constants
import com.saadahmedsoft.tinydb.TinyDB
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class IncomingCallActivity : BaseActivity<ActivityIncomingCallBinding>(ActivityIncomingCallBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding?
        get() = null

    private var uid = ""
    private var name = ""
    private var email = ""
    private var meetingId = ""

    private lateinit var myProfile: User

    private lateinit var player: RingtonePlayer

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        myProfile = TinyDB.getInstance(this).getObject("my_profile", User::class.java)
        player = RingtonePlayer.getInstance(this)
        player.ring()

        delay(30000) {
            player.stop()
        }

        uid = intent.getStringExtra("uid")!!
        name = intent.getStringExtra("name")!!
        email = intent.getStringExtra("email")!!
        meetingId = intent.getStringExtra("meetingId")!!

        binding.item = User(
            uid,
            name,
            email
        )

        binding.btnDisconnect.onClicked {
            onHangUpButtonClicked()
        }

        binding.btnReceive.onClicked {
            onReceiveButtonClicked()
        }
    }

    override fun observeData() {}

    private fun onReceiveButtonClicked() {
        player.stop()
        sendRemoteMessage("accepted")
        //initiate meeting
        initiateMeeting()
    }

    private fun onHangUpButtonClicked() {
        player.stop()
        sendRemoteMessage("rejected")
        onBackButtonPressed()
    }

    private fun sendRemoteMessage(status: String) {
        val myProfile = TinyDB.getInstance(this).getObject<User>("my_profile", User::class.java)
        val data = Data(
            status,
            uid,
            myProfile.name!!,
            myProfile.email!!,
            myProfile.uid!!
        )

        val body = MessageBody("/topics/topseba", data)

        val call = RetroClient.getInstance.sendRemoteMessage(Constants.Api.FCM_KEY, body)
        call.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (!response.isSuccessful) {
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
        userInfo.putString("displayName", myProfile.name)
        userInfo.putString("email", myProfile.email)

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
                if (type == "hangup") {
                    onBackButtonPressed()
                    player.stop()
                    longToast("Call Cancelled")
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