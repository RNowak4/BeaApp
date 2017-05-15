package com.radek.beaapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.radek.beaapp.R;
import com.radek.beaapp.utils.BackendClient;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void performLogin(View view) {
        final String login = ((TextView) findViewById(R.id.login)).getText().toString();
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final String password = ((TextView) findViewById(R.id.password)).getText().toString();
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Logowanie...", true);

        loginButton.setEnabled(false);
        dialog.show();

        final RequestParams loginParams = new RequestParams();
        loginParams.put(LOGIN, login);
        loginParams.put(PASSWORD, password);

        BackendClient.post("/login", loginParams, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = LoginActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas logowania. Spróbuj ponownie." + statusCode, Toast.LENGTH_LONG).show();
                Log.e("InzApp", "Caught Error", throwable);
                loginButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = LoginActivity.this.getApplicationContext();
                final Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Udalo sie zalogowac", Toast.LENGTH_LONG).show();
                loginButton.setEnabled(true);
                dialog.dismiss();
                context.startActivity(intent);
            }
        });
    }

    public void performRegister(View view) {
        final Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}