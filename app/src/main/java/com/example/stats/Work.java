package com.example.stats;

import android.content.Context;
import android.hardware.Sensor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class Work {
    private MediaPlayer sirSound;

    Handler handler = new Handler(Looper.getMainLooper());

    public void start(Context context){
        sirSound = MediaPlayer.create(context, R.raw.sound);
        soundPlayButton(sirSound);
    }
    private void soundPlayButton(MediaPlayer sound) {
        sound.start();
    }

}