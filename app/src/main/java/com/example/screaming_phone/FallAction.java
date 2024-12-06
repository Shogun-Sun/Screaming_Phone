package com.example.screaming_phone;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class FallAction {
    private static boolean isPlaying = false;
    private static MediaPlayer sirSound;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void start(Context context) {
        if (isPlaying) {
            Log.d("Work", "Sound is already playing");
            return;
        }

        isPlaying = true;
        sirSound = MediaPlayer.create(context, R.raw.sound);

        if (sirSound == null) {
            Log.e("Work", "Failed to load sound");
            isPlaying = false;
            return;
        }

        sirSound.setOnCompletionListener(mp -> {
            isPlaying = false;
            Log.d("Work", "Sound completed");
            mp.release();
        });

        sirSound.start();
        Log.d("Work", "Sound started");
    }

    public static void stop() {
        if (sirSound != null && sirSound.isPlaying()) {
            sirSound.stop();
            isPlaying = false;
            sirSound.release();
            Log.d("Work", "Sound stopped");
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
}
