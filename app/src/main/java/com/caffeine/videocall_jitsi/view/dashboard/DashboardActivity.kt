package com.caffeine.videocall_jitsi.view.dashboard

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.ActivityDashboardBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.saadahmedsoft.base.BaseActivity

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding
        get() = binding.appToolbar

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        //
    }

    override fun observeData() {}
}