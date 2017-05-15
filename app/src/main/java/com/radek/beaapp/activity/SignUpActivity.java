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

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {
    private static final String LOGIN = "userName";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void performSignUp(View view) {
        final String email = ((TextView) findViewById(R.id.email)).getText().toString();
        final String login = ((TextView) findViewById(R.id.login)).getText().toString();
        final Button loginButton = (Button) findViewById(R.id.sign_in_button);
        final String password = ((TextView) findViewById(R.id.password)).getText().toString();
        final String passwordRepeat = ((TextView) findViewById(R.id.passwordRepeat)).getText().toString();

        if (!password.equals(passwordRepeat)) {
            Toast.makeText(this, "Wprowadzone hasła nie są identyczne!", Toast.LENGTH_LONG);
            return;
        }

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Rejestrowanie...", true);

        loginButton.setEnabled(false);
        dialog.show();

        final RequestParams loginParams = new RequestParams();
        loginParams.put(LOGIN, login);
        loginParams.put(PASSWORD, password);
        loginParams.put(EMAIL, email);

        BackendClient.post("/user", loginParams, new TextHttpResponseHandler() {
            
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final Context context = SignUpActivity.this.getApplicationContext();
                Toast.makeText(context, "Bład podczas rejestracji. Spróbuj ponownie.", Toast.LENGTH_LONG).show();
                loginButton.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Context context = SignUpActivity.this.getApplicationContext();
                final Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Rejestracja przebiegła pomyślnie", Toast.LENGTH_LONG).show();
                loginButton.setEnabled(true);
                dialog.dismiss();
                context.startActivity(intent);
            }
        });
    }
}
