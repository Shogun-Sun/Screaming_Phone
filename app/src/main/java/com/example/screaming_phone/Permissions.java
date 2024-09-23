package com.example.screaming_phone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    public static int MY_PERMISSION_REQUEST_POST_NOTIFICATIONS = 1342;

    public static void permission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(activity ,
                    new String[] {Manifest.permission.POST_NOTIFICATIONS}, MY_PERMISSION_REQUEST_POST_NOTIFICATIONS);
            }

        }
}


