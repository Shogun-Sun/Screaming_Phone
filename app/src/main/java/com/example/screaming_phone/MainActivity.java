package com.example.screaming_phone;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements FallDetection.FallDetectionListener{
    private FallDetection fallDetection;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fallDetection = new FallDetection(this, this);
        fallDetection.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        fallDetection.stop();
    }

    @Override
    public void onFallDetected(){
        Toast.makeText(this, "Падение обнаружено!", Toast.LENGTH_SHORT).show();
    }

}