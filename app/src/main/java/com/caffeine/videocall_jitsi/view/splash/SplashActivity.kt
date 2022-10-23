package com.caffeine.videocall_jitsi.view.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.auth.AuthActivity
import com.caffeine.videocall_jitsi.view.dashboard.DashboardActivity
import com.saadahmedsoft.base.helper.delay
import com.saadahmedsoft.shortintent.Anim
import com.saadahmedsoft.shortintent.ShortIntent

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager.getInstance(this)

        delay(500) {
            ShortIntent.getInstance(this)
                .addDestination(
                    if (sessionManager.uid == "") {
                        AuthActivity::class.java
                    } else DashboardActivity::class.java
                )
                .addTransition(Anim.FADE)
                .finish(this)
        }
    }
}