package com.caffeine.videocall_jitsi.view.dashboard.tabs

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.FragmentHomeBinding
import com.saadahmedsoft.base.BaseFragment
import com.saadahmedsoft.base.utils.Constants.Booleans.FALSE

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override val title: String
        get() = "Home"
    override val isBackButtonVisible: Boolean
        get() = FALSE

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        //
    }

    override fun observeData() {}
}