package com.saadahmedsoft.base.helper

import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.saadahmedsoft.shortintent.R

fun navigate(view: View, id: Int) {
    val navBuilder = NavOptions.Builder()
    navBuilder
        .setEnterAnim(R.anim.animate_fade_enter)
        .setExitAnim(R.anim.animate_fade_exit)
        .setPopEnterAnim(R.anim.animate_fade_enter)
        .setPopExitAnim(R.anim.animate_fade_exit)

    Navigation.findNavController(view).navigate(id, null, navBuilder.build())
}