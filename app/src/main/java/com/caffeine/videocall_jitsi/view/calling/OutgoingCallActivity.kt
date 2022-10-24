package com.caffeine.videocall_jitsi.view.calling

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.ActivityOutgoingCallBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.services.model.common.User
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.onClicked

class OutgoingCallActivity : BaseActivity<ActivityOutgoingCallBinding>(ActivityOutgoingCallBinding::inflate) {
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
    }

    override fun observeData() {}

    private fun onHangUpButtonClicked() {
        onBackButtonPressed()
    }
}