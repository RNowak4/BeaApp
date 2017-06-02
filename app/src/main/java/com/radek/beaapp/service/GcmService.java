package com.radek.beaapp.service;

import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.radek.beaapp.R;

public class GcmService extends GcmListenerService {
    private static final String TAG = "GcmService";
    private NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                    .setContentTitle("Wiadomosc o kradziezy!")
                    .setContentText("Wlasnie ktos ukradl Ci motocykl.")
                    .setLights(Color.RED, 3000, 3000)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

    public GcmService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        final String message = data.getString("score");
        Log.d(TAG, "Message from: " + from);
        Log.d(TAG, "Message: " + message);
        int mNotificationId = 1;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    public void onDeletedMessages() {
    }

    @Override
    public void onMessageSent(String msgId) {
    }

    @Override
    public void onSendError(String msgId, String error) {
    }
}