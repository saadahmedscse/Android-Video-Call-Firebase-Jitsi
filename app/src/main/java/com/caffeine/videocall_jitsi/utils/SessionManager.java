package com.caffeine.videocall_jitsi.utils;

import android.content.Context;

import com.saadahmedsoft.tinydb.TinyDB;

public class SessionManager {

    private static volatile TinyDB tinyDB;

    public static SessionManager getInstance(Context context) {
        tinyDB = TinyDB.getInstance(context);
        return new SessionManager();
    }

    public void setNumber(String number) {
        tinyDB.putString("static_number", number).apply();
    }

    public String getNumber() {
        return tinyDB.getString("static_number", "");
    }

    public void setToken(String token) {
        tinyDB.putString("static_token", "Bearer " + token).apply();
    }

    public String getToken() {
        return tinyDB.getString("static_token", "");
    }

    public void removeSession() {
        tinyDB.putString("static_number", "")
                .putString("static_token", "").apply();
    }
}
