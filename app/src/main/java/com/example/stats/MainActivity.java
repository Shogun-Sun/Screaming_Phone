package com.example.stats;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
//import com.example.stats.Permissions;



import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.stats.Permissions.MY_PERMISSION_REQUEST_POST_NOTIFICATIONS;


public class MainActivity extends AppCompatActivity {
    private static CharSequence text = "AVP";
    private static int duration = Toast.LENGTH_SHORT;

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
    Bitmap bitmap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissions.permission(this);



        mXValueText = findViewById(R.id.value_x);
        mYValueText = findViewById(R.id.value_y);
        mZValueText = findViewById(R.id.value_z);
        sirSound = MediaPlayer.create(this, R.raw.sound);
        SensorHandler = new SensorHandler(this, mXValueText, mYValueText, mZValueText, sirSound);
        ImageView imageView = findViewById(R.id.your_file);
        Button button_done = findViewById(R.id.button_done);



        //Uploading files

        ActivityResultLauncher<Intent> activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();

                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_POST_NOTIFICATIONS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)) {

                Toast.makeText(this, "Пожалуйста, предоставьте разрешение в настройках приложения", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {

                Toast.makeText(this, "Пожалуйста, предоставьте разрешение в настройках приложения", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    };
}