package com.caffeine.videocall_jitsi.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("topseba")
    }
}