package com.caffeine.videocall_jitsi.view.auth.tabs.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.FragmentLoginBinding
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.auth.AuthActivity
import com.caffeine.videocall_jitsi.view.dashboard.DashboardActivity
import com.caffeine.videocall_jitsi.view.utils.CustomDialog
import com.google.firebase.auth.FirebaseAuth
import com.saadahmedsoft.base.BaseFragment
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.shortintent.Anim
import com.saadahmedsoft.shortintent.ShortIntent
import com.topsebadoctor.helper.gText

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override val title: String
        get() = "Login"
    override val isBackButtonVisible: Boolean
        get() = false

    private lateinit var dialog: CustomDialog

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        dialog = CustomDialog.getInstance(requireContext())

        binding.btnLogin.onClicked {
            if (validate()) {
                loginUser()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AuthActivity).setDefaultTab()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun observeData() {}

    private fun validate(): Boolean {
        when {
            binding.etEmail.gText().isBlank() -> {
                binding.etEmail.error = "Email is required"
                return false
            }
            binding.etPassword.gText().isBlank() -> {
                binding.etPassword.error = "Password is required"
                return false
            }
        }
        return true
    }

    private fun loginUser() {
        dialog.showProgressDialog()
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(binding.etEmail.gText(), binding.etPassword.gText())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val sessionManager = SessionManager.getInstance(requireContext())
                    sessionManager.uid = auth.uid

                    ShortIntent.getInstance(requireActivity())
                        .addDestination(DashboardActivity::class.java)
                        .addTransition(Anim.FADE)
                        .finish(requireActivity())
                    dialog.dismissDialog()
                }
                else {
                    dialog.dismissDialog()
                    longSnackBar(it.exception?.localizedMessage!!)
                }
            }
    }
}