package com.example.stats;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.TextView;

public class SensorHandler implements SensorEventListener {
    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private TextView mXValueText;
    private TextView mYValueText;
    private TextView mZValueText;
    public SensorHandler(Context context, TextView xValueText, TextView yValueText, TextView zValueText, MediaPlayer sirSound) {
        this.mXValueText = xValueText;
        this.mYValueText = yValueText;
        this.mZValueText = zValueText;
        this.mContext = context;

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerListener() {
        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mXValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_X]));
            mYValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Y]));
            mZValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Z]));

            if (event.values[SensorManager.DATA_Y] <= -13) {
                    new Work().start(mContext);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

