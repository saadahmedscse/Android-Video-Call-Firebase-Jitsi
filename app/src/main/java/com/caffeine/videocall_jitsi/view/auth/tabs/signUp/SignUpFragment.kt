package com.caffeine.videocall_jitsi.view.auth.tabs.signUp

import android.os.Bundle
import com.caffeine.videocall_jitsi.databinding.FragmentSignUpBinding
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.dashboard.DashboardActivity
import com.caffeine.videocall_jitsi.view.utils.CustomDialog
import com.google.firebase.auth.FirebaseAuth
import com.saadahmedsoft.base.BaseFragment
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.utils.Constants.Database.userRef
import com.saadahmedsoft.shortintent.Anim
import com.saadahmedsoft.shortintent.ShortIntent
import com.topsebadoctor.helper.gText

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    override val title: String
        get() = "Sign Up"
    override val isBackButtonVisible: Boolean
        get() = true

    private lateinit var dialog: CustomDialog

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        dialog = CustomDialog.getInstance(requireContext())

        binding.btnSignUp.onClicked {
            if (validate()) {
                createAccount()
            }
        }
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
            binding.etPassword.gText().length < 6 -> {
                binding.etPassword.error = "Password must be more than 6 digit long"
                return false
            }
            binding.etConfirmPassword.gText().isBlank() -> {
                binding.etConfirmPassword.error = "Confirm password is required"
                return false
            }
            binding.etPassword.gText() != binding.etConfirmPassword.gText() -> {
                binding.etConfirmPassword.error = "Password did not match"
                return false
            }
        }
        return true
    }

    private fun createAccount() {
        dialog.showProgressDialog()
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(binding.etEmail.gText(), binding.etPassword.gText())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUserData(auth)
                }
                else {
                    longSnackBar(it.exception?.localizedMessage!!)
                    dialog.dismissDialog()
                }
            }
    }

    private fun updateUserData(auth: FirebaseAuth) {
        val user = HashMap<String, String>()
        user["uid"] = auth.currentUser?.uid!!
        user["email"] = binding.etEmail.gText()
        user["password"] = binding.etPassword.gText()

        userRef.child(auth.currentUser?.uid!!).setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val sessionManager = SessionManager.getInstance(requireContext())
                    sessionManager.uid = auth.uid

                    ShortIntent.getInstance(requireActivity())
                        .addDestination(DashboardActivity::class.java)
                        .addTransition(Anim.FADE)
                        .finish(requireActivity())
                }
                else {
                    longSnackBar(it.exception?.localizedMessage!!)
                    dialog.dismissDialog()
                }
            }
    }
}