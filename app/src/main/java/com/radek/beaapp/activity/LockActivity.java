package com.radek.beaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.radek.beaapp.R;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_beacons);
    }

    public void getBack(View view) {
        final Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}
