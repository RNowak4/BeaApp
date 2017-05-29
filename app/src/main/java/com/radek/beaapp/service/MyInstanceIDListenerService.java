package com.radek.beaapp.service;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.radek.beaapp.intent.RegistrationIntentService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAA");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
