package com.caffeine.videocall_jitsi.view.auth.tabs.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.FragmentSignUpBinding
import com.saadahmedsoft.base.BaseFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    override val title: String
        get() = "Sign Up"
    override val isBackButtonVisible: Boolean
        get() = true

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        //
    }

    override fun observeData() {}
}