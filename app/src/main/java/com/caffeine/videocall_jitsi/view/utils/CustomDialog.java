package com.caffeine.videocall_jitsi.view.utils;

import android.content.Context;

import com.caffeine.videocall_jitsi.R;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;

public class CustomDialog {

    private static CustomDialog instance = null;
    private final PopupDialog dialog;

    private CustomDialog(Context context) {
        dialog = PopupDialog.getInstance(context);
    }

    public static CustomDialog getInstance(Context context) {
        if (instance == null) {
            instance = new CustomDialog(context);
        }
        return instance;
    }

    public void showProgressDialog() {
        dialog.setStyle(Styles.LOTTIE_ANIMATION)
                .setLottieRawRes(R.raw.loading)
                .setCancelable(false)
                .showDialog();
    }

    public void dismissDialog() {
        dialog.dismissDialog();
    }
}
