package com.caffeine.videocall_jitsi.view.calling

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.caffeine.videocall_jitsi.api.RetroClient
import com.caffeine.videocall_jitsi.databinding.ActivityOutgoingCallBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.services.model.api.Data
import com.caffeine.videocall_jitsi.services.model.api.MessageBody
import com.caffeine.videocall_jitsi.services.model.api.MessageResponse
import com.caffeine.videocall_jitsi.services.model.common.User
import com.google.firebase.messaging.FirebaseMessaging
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.utils.Constants.Api.FCM_KEY
import com.saadahmedsoft.tinydb.TinyDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutgoingCallActivity : BaseActivity<ActivityOutgoingCallBinding>(ActivityOutgoingCallBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding?
        get() = null

    private var uid = ""
    private var name = ""
    private var email = ""

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        uid = intent.getStringExtra("uid")!!
        name = intent.getStringExtra("name")!!
        email = intent.getStringExtra("email")!!

        binding.item = User(
            uid,
            name,
            email
        )

        binding.btnDisconnect.onClicked {
            onHangUpButtonClicked()
        }

        sendCallToRemoteUser()
    }

    override fun observeData() {}

    private fun onHangUpButtonClicked() {
        onBackButtonPressed()
    }

    private fun sendCallToRemoteUser() {
        val myProfile = TinyDB.getInstance(this).getObject<User>("my_profile", User::class.java)
        val data = Data(
            uid,
            myProfile.name!!,
            myProfile.email!!,
            myProfile.uid!!
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
                    startRingingSound()
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

    private fun startRingingSound() {
        //
    }
}