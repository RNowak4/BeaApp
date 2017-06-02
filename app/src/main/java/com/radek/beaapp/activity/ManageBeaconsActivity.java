package com.radek.beaapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.radek.beaapp.R;
import com.radek.beaapp.utils.BackendClient;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import cz.msebera.android.httpclient.Header;

public class ManageBeaconsActivity extends AppCompatActivity implements BeaconConsumer {
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_beacons);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public void addBeacon(View view) {
        final Button sendBeaconButton = (Button) findViewById(R.id.sendBeacon);
        final RequestParams surveyParams = new RequestParams();
        final String beaconName = ((TextView) findViewById(R.id.beaconId)).getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Dodawanie beacona...", true);
        surveyParams.put("beaconName", beaconName);

        sendBeaconButton.setEnabled(false);
        dialog.show();

        BackendClient.post("/beacon", surveyParams, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = ManageBeaconsActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas dodawania beacona. Sproboj ponownie.", Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
                sendBeaconButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = ManageBeaconsActivity.this.getApplicationContext();
                final Intent intent = new Intent(context, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Poprawnie dodano beacon", Toast.LENGTH_LONG).show();
                sendBeaconButton.setEnabled(true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public void deleteBeacon(View view) {
        final Button sendBeaconButton = (Button) findViewById(R.id.sendBeacon);
        final RequestParams surveyParams = new RequestParams();
        final String beaconName = ((TextView) findViewById(R.id.beaconId)).getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Usuwanie beacona...", true);
        surveyParams.put("beaconName", beaconName);

        sendBeaconButton.setEnabled(false);
        dialog.show();

        BackendClient.delete("/beacon", surveyParams, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = ManageBeaconsActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas usuwania beacona. Sproboj ponownie.", Toast.LENGTH_LONG).show();
                throwable.printStackTrace();
                sendBeaconButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = ManageBeaconsActivity.this.getApplicationContext();
                final Intent intent = new Intent(context, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Poprawnie dodano beacon", Toast.LENGTH_LONG).show();
                sendBeaconButton.setEnabled(true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public void getBack(View view) {
        final Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            final Context context = ManageBeaconsActivity.this.getApplicationContext();

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Toast.makeText(context, "The first beacon I see is about "
                            + beacons.iterator().next().getDistance()
                            + " meters away.", Toast.LENGTH_LONG).show();
                }
            }
        });
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            final Context context = ManageBeaconsActivity.this.getApplicationContext();

            @Override
            public void didEnterRegion(Region region) {
                Toast.makeText(context, "Zauwazono Beacon!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void didExitRegion(Region region) {
                Toast.makeText(context, "Beacon Zniknoul!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });
    }
}