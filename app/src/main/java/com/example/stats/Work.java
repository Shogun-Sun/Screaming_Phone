package com.example.stats;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Work {
    public static boolean is_playing = false;
    private static Timer timer;
    private static MediaPlayer sirSound;
    Handler handler = new Handler(Looper.getMainLooper());

    public static void start(Context context){
        timer = new Timer();
        is_playing = true;
        sirSound = MediaPlayer.create(context, R.raw.sound);
        long sec = sirSound.getDuration();
        sirSound.start();
        Log.i("Work","I AM CALLED! ");
        TimerTask task = new TimerTask() {
            public void run() {
                is_playing = false;
                timer.cancel();
                timer.purge();
                Log.i("Work","END!");
            }
        };
        timer.schedule(task, sec, 1);








    }


}

