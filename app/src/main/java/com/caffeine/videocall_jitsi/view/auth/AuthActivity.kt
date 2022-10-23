package com.caffeine.videocall_jitsi.view.auth

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.ActivityAuthBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.caffeine.videocall_jitsi.helper.changeTextColor
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.helper.setBackground

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding
        get() = binding.appToolbar

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        binding.btnLogin.onClicked {
            onLoginTabClicked()
        }

        binding.btnSignUp.onClicked {
            onSignUpTabClicked()
        }
    }

    override fun observeData() {}

    private fun onLoginTabClicked() {
        onTabClicked(binding.btnLogin, binding.btnSignUp)
        if (binding.appToolbar.toolbarTitle.text != "Login") {
            onBackPressed()
        }
    }

    private fun onSignUpTabClicked() {
        onTabClicked(binding.btnSignUp, binding.btnLogin)
        navigate(R.id.signUpFragment)
    }

    private fun onTabClicked(tab1: TextView, tab2: TextView) {
        tab1.changeTextColor(this, R.color.static_white)
        tab2.changeTextColor(this, R.color.colorDarkGrey)
        tab1.setBackground(R.drawable.ripple_bg_main_5)
        tab2.setBackground(R.drawable.ripple_bg_light_grey_5)
    }

    private fun navigate(@IdRes id: Int) {
        val myNavHostFragment = supportFragmentManager.findFragmentById(R.id.authContainerView) as NavHostFragment
        val navController = myNavHostFragment.navController

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.fade_enter)
            .setExitAnim(R.anim.fade_exit)
            .setPopEnterAnim(R.anim.fade_enter)
            .setPopExitAnim(R.anim.fade_exit)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        navController.navigate(id, null, options)
    }

    fun setDefaultTab() {
        onTabClicked(binding.btnLogin, binding.btnSignUp)
    }
}