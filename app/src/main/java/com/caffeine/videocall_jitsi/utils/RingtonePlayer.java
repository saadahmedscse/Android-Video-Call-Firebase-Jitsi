package com.caffeine.videocall_jitsi.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.caffeine.videocall_jitsi.R;

public class RingtonePlayer {

    private final MediaPlayer mediaPlayer;

    private RingtonePlayer(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.ringtone);
    }

    public static RingtonePlayer getInstance(Context context) {
        RingtonePlayer instance;
        instance = new RingtonePlayer(context);
        return instance;
    }

    public void ring() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }
}
