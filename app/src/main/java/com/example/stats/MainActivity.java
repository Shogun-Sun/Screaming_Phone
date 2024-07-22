package com.example.stats;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /** Объект типа сенсор менеджер */
    SensorManager mSensorManager;
    /** Создали объект типа сенсор для получения данных угла наклона телефона */
    Sensor mAccelerometerSensor;
    Sensor mMagneticFieldSensor;
    /** Вывод полученных данных*/
    TextView mXValueText;
    TextView mYValueText;
    TextView mZValueText;
    private MediaPlayer sirSound;
    private SensorHandler SensorHandler;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mXValueText = findViewById(R.id.value_x);
        mYValueText = findViewById(R.id.value_y);
        mZValueText = findViewById(R.id.value_z);
        sirSound = MediaPlayer.create(this, R.raw.sound);
        SensorHandler = new SensorHandler(this, mXValueText, mYValueText, mZValueText, sirSound);

//        findViewById(R.id.value_x).setOnClickListener(v -> {
//            Intent serviceIntent = new Intent(this, ForegroundService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(serviceIntent);
//            } else {
//                startService(serviceIntent);
//            }
//        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorHandler.registerListener();
    }

    @Override
    protected void onResume() {

        super.onResume();
        SensorHandler.registerListener();
    }
}