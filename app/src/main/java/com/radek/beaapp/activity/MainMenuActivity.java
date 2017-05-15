package com.radek.beaapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;
import com.radek.beaapp.R;
import com.radek.beaapp.utils.BackendClient;

import cz.msebera.android.httpclient.Header;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void fillSurvey(View view) {
        final Intent intent = new Intent(this, ManageBeaconsActivity.class);
        startActivity(intent);
    }

    public void maintainLocks(View view) {
        final Intent intent = new Intent(this, LockActivity.class);
        startActivity(intent);
    }

    public void performLogout(View view) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Wylogowywanie...", true);

        dialog.show();

        BackendClient.get("/logout", null, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = MainMenuActivity.this.getApplicationContext();
                dialog.dismiss();
                Toast.makeText(context, "Bład podczas wylogowywania. Sprobuj ponownie.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = MainMenuActivity.this.getApplicationContext();
                final Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BackendClient.clearCookieStore();
                dialog.dismiss();
                Toast.makeText(context, "Udalo sie wylogowac", Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }

    public void getBack(View view) {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}