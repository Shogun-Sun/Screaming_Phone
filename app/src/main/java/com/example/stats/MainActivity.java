package com.example.stats;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Service;

import com.example.stats.R;

import java.util.List;
public class MainActivity extends Activity implements SensorEventListener {

    /** Объект типа сенсор менеджер */
    SensorManager mSensorManager;
    /** Создали объект типа сенсор для получения данных угла наклона телефона */
    Sensor mAccelerometerSensor;
    Sensor mMagneticFieldSensor;
    /** Наши текствью в которые будем все выводить */
    TextView mXValueText;
    TextView mYValueText;
    TextView mZValueText;
    private MediaPlayer sirSound;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sirSound = MediaPlayer.create(this, R.raw.sound);
        // присвоили менеджеру работу с серсором
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // создали список сенсоров для записи и сортировки
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        // делаем проверку если больше нуля значит все хорошо и начинаем обрабатывать работу датчика
        if (sensors.size() > 0) {
            // форич для зацикливания работы, что бы не единожды выполнялось, а постоянно
            for (Sensor sensor : sensors) {
                // берем данные с акселерометра
                switch (sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        // если пусто значит возвращаем значения сенсора
                        if (mAccelerometerSensor == null)
                            mAccelerometerSensor = sensor;
                        break;
                    default:
                        break;
                }
            }
        }
        // привязываем наши объекты к нашей разметке
        mXValueText = (TextView) findViewById(R.id.value_x);
        mYValueText = (TextView) findViewById(R.id.value_y);
        mZValueText = (TextView) findViewById(R.id.value_z);
    }

    @Override
    protected void onPause() {

        //говорим что данные будем получать из этого класса
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        //регистрируем сенсоры в объекты сенсора
        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagneticFieldSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        //создали массив в которые будем записывать наши данные полученые с датчиков
        float[] values = event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                //собственно выводим все полученые параметры в текствьюшки наши
                mXValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_X]));
                mYValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Y]));
                mZValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Z]));
            }
            break;
        }
        if(event.values[SensorManager.DATA_Y] <= -15){
            soundPlayButton(sirSound);
        }


    }

    private void soundPlayButton(MediaPlayer sound) {
        sound.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}