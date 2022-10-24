package com.caffeine.videocall_jitsi.utils;

import android.content.Context;

import com.saadahmedsoft.tinydb.TinyDB;

public class SessionManager {

    private static TinyDB tinyDB;

    public static SessionManager getInstance(Context context) {
        tinyDB = TinyDB.getInstance(context);
        return new SessionManager();
    }

    public void setUid(String uid) {
        tinyDB.putString("static_uid", uid).apply();
    }

    public String getUid() {
        return tinyDB.getString("static_uid", "");
    }
}
