package com.caffeine.videocall_jitsi.utils;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import com.caffeine.videocall_jitsi.R;

public class RingtonePlayer {

    private final MediaPlayer mediaPlayer;

    private RingtonePlayer(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.ringtone);
    }

    @NonNull
    public static RingtonePlayer getInstance(Context context) {
        return new RingtonePlayer(context);
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
