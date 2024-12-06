package com.example.screaming_phone;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class FallDetection implements SensorEventListener {

    private static final float FALL_THRESHOLD = 2.5f; // Порог для обнаружения свободного падения (м/с²).
    private static final float IMPACT_THRESHOLD = 15.0f; // Порог для определения удара (м/с²).
    private static final long FALL_DELAY_MS = 3000;

    private final SensorManager sensorManager;
    private final Sensor accelerometer;

    //Интерфейс для передачи информации о событии.
    private final FallDetectionListener listener;

    private boolean isFalling = false;
    private long lastFallTime = 0;


    public interface FallDetectionListener{
        void onFallDetected();
    }

    //Конструктор для инициализации FallDetection
    public FallDetection(Context context, FallDetectionListener listener) {
        this.listener = listener; //Слушатель событий

        //Доступ к системе сенсоро через контекст прилодения.
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //Акселерометр
        accelerometer = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;

        if(accelerometer == null) {
            throw new UnsupportedOperationException("Акселерометр не доступен");
        }
    }

    public void start(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }
    public void stop(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //Значения с акселерометра.
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //Модуль ускорения (векторное сложение ускорений по осям)
            float acceleration = (float) Math.sqrt(x*x + y*y + z*z);

            if(System.currentTimeMillis() - lastFallTime < FALL_DELAY_MS){
                return;
            }
            if(acceleration < FALL_THRESHOLD && !isFalling){
                isFalling = true;
                FallAction.start((Context) listener);
                Log.d("Fall detected", "Current acceleration: " + acceleration);
            } else if (isFalling && acceleration > IMPACT_THRESHOLD) {
                isFalling = false;
                Log.d("Fall undetected", "stop");
                lastFallTime = System.currentTimeMillis();
                listener.onFallDetected();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
