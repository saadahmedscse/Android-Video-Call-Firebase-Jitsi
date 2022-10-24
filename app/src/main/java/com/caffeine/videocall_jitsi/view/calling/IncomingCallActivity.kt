package com.caffeine.videocall_jitsi.view.calling

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.ActivityIncomingCallBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.services.model.common.User
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.onClicked

class IncomingCallActivity : BaseActivity<ActivityIncomingCallBinding>(ActivityIncomingCallBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding?
        get() = null

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        binding.item = User(
            intent.getStringExtra("uid"),
            intent.getStringExtra("name"),
            intent.getStringExtra("email")
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
        //
    }

    private fun onHangUpButtonClicked() {
        onBackButtonPressed()
    }
}