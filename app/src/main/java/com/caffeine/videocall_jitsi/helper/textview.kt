package com.caffeine.videocall_jitsi.helper

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.changeTextColor(context: Context, @ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}