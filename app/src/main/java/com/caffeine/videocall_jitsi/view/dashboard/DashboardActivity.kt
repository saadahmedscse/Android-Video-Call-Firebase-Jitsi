package com.caffeine.videocall_jitsi.view.dashboard

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.ActivityDashboardBinding
import com.caffeine.videocall_jitsi.databinding.AppToolbarBinding
import com.saadahmedsoft.base.BaseActivity
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener


class DashboardActivity : BaseActivity<ActivityDashboardBinding>(ActivityDashboardBinding::inflate) {
    override val toolbarBinding: AppToolbarBinding
        get() = binding.appToolbar

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            PopupDialog.getInstance(this)
                .setStyle(Styles.STANDARD)
                .setHeading("Require Permission")
                .setDescription("Your app requires permission to display over other app to use video call service")
                .setPopupDialogIcon(R.drawable.img_layer)
                .setNegativeButtonText("Deny")
                .setPositiveButtonText("Allow")
                .setNegativeButtonBackground(R.drawable.ripple_bg_light_grey_5)
                .setPositiveButtonBackground(R.drawable.ripple_bg_main_5)
                .showDialog(object : OnDialogButtonClickListener() {
                    override fun onNegativeClicked(dialog: Dialog?) {
                        super.onNegativeClicked(dialog)
                    }

                    override fun onPositiveClicked(dialog: Dialog?) {
                        super.onPositiveClicked(dialog)
                        startActivityForResult(intent, 0)
                    }
                })
        }
    }

    override fun observeData() {}
}