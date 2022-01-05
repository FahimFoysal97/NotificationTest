package com.example.notificationtest.data.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.notificationtest.MainActivity;
import com.example.notificationtest.R;
import com.example.notificationtest.util.Constant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotification extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        long[] pattern = {0, 1000, 500, 1000};
        String NOTIFICATION_CHANNEL_ID = Constant.CHANNEL_ID_PUSH_NOTIFICATION;
        String title = remoteMessage.getNotification().getTitle();
        String content = remoteMessage.getNotification().getBody();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, Constant.CHANNEL_NAME_PUSH_NOTIFICATION, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(pattern);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            notificationChannel.canBypassDnd();
        }

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(notificationPendingIntent)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_baseline_mail_24)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.raod))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.raod))
                        .bigLargeIcon(null));
        notificationManager.notify(Constant.PUSH_NOTIFICATION_ID, notificationBuilder.build());
    }
}
