package com.radek.beaapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.radek.beaapp.R;
import com.radek.beaapp.utils.BackendClient;

import cz.msebera.android.httpclient.Header;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }

    public void getBack(View view) {
        final Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }


    public void deleteLock(View view) {
        final Button sendBeaconButton = (Button) findViewById(R.id.sendBeacon);
        final String beaconName = ((TextView) findViewById(R.id.beaconId)).getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Zakladanie blokady...", true);

        sendBeaconButton.setEnabled(false);
        dialog.show();

        BackendClient.delete("/lock/" + beaconName, null, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = LockActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas dodawania beacona. Sproboj ponownie.", Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
                sendBeaconButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = LockActivity.this.getApplicationContext();
                final Intent intent = new Intent(context, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Poprawnie dodano beacon", Toast.LENGTH_LONG).show();
                sendBeaconButton.setEnabled(true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public void setLock(View view) {
        final Button sendBeaconButton = (Button) findViewById(R.id.sendBeacon);
        final String beaconName = ((TextView) findViewById(R.id.beaconId)).getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Zakladanie blokady...", true);

        sendBeaconButton.setEnabled(false);
        dialog.show();

        BackendClient.post("/lock/" + beaconName, null, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = LockActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas dodawania beacona. Sproboj ponownie.", Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
                sendBeaconButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = LockActivity.this.getApplicationContext();
                final Intent intent = new Intent(context, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Poprawnie dodano beacon", Toast.LENGTH_LONG).show();
                sendBeaconButton.setEnabled(true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}
