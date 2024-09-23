//package com.example.stats;
//
//import android.app.*;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//public class ForegroundService extends Service {
//    private static final String CHANNEL_ID = "foregroundServiceChannel";
//    private static void start(Activity host){
//        Intent intent = new Intent(host, ForegroundService.class);
//        ContextCompat.startForegroundService(host, intent);
//    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        createNotificationChannel();
//
//        String input = intent.getStringExtra("inputExtra");
//        createNotificationChannel();
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Screaming Phone")
//                .setContentText(input)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentIntent(pendingIntent)
//                .build();
//        startForeground(1, notification);
//        return  START_NOT_STICKY;
//    }
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Foreground Service", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//    }
//
//}
