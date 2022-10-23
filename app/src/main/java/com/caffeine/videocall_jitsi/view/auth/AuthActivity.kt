package com.caffeine.videocall_jitsi.view.auth

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.ActivityAuthBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.saadahmedsoft.base.BaseActivity

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding
        get() = binding.appToolbar

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        //
    }

    override fun observeData() {}
}