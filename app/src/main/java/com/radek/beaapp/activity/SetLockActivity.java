package com.radek.beaapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;
import com.radek.beaapp.R;
import com.radek.beaapp.utils.BackendClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SetLockActivity extends AppCompatActivity {
    private List<String> registeredBeacons = new ArrayList<>();

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lock);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, registeredBeacons);
        final ListView beaconsListView = (ListView) findViewById(R.id.beaconsList);
        beaconsListView.setAdapter(adapter);
        addBeacon(beaconsListView);
    }

    public synchronized void addBeacon(final ListView beaconsListView) {
        BackendClient.get("/beacon", null, new TextHttpResponseHandler() {
            private final static String BEACON_NAME = "name";

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = SetLockActivity.this.getApplicationContext();
                Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = SetLockActivity.this.getApplicationContext();
                Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final String beaconName = jsonArray.getJSONObject(0).getString(BEACON_NAME);
                        registeredBeacons.add(beaconName);
                    }
                    beaconsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final String beaconId = (String) beaconsListView.getItemAtPosition(i);
//                            final ProgressDialog dialog = ProgressDialog.show(context, "", "Logowanie...", true);

                            BackendClient.post("/lock/" + beaconId, null, new TextHttpResponseHandler() {

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    final Context context = SetLockActivity.this.getApplicationContext();
                                    Toast.makeText(context, "Bład podczas zakladania blokady.", Toast.LENGTH_LONG).show();
//                                    dialog.dismiss();
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    final Context context = SetLockActivity.this.getApplicationContext();
                                    final Intent intent = new Intent(context, MainMenuActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Toast.makeText(context, "Założenie blokady przebiegło pomyślnie", Toast.LENGTH_LONG).show();
//                                    dialog.dismiss();
                                    context.startActivity(intent);
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getBack(View view) {
        final Intent intent = new Intent(this, LockActivity.class);
        startActivity(intent);
    }
}