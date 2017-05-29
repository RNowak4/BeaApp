package com.radek.beaapp.service;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class GcmService extends GcmListenerService {
    private static final String TAG = "GcmService";

    public GcmService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        final String message = data.getString("score");
        Log.d(TAG, "Message from: " + from);
        Log.d(TAG, "Message: " + message);
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