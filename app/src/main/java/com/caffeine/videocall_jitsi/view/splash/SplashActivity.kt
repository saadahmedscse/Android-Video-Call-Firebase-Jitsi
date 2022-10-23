package com.caffeine.videocall_jitsi.view.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.caffeine.videocall_jitsi.R
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

        delay(500) {
            ShortIntent.getInstance(this)
                .addDestination(DashboardActivity::class.java)
                .addTransition(Anim.FADE)
                .finish(this)
        }
    }
}