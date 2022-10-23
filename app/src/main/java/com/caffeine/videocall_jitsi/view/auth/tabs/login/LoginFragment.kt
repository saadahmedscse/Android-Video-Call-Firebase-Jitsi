package com.caffeine.videocall_jitsi.view.auth.tabs.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.FragmentLoginBinding
import com.saadahmedsoft.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override val title: String
        get() = "Login"
    override val isBackButtonVisible: Boolean
        get() = false

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        //
    }

    override fun observeData() {}
}