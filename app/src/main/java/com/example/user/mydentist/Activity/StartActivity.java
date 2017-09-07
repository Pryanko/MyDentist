package com.example.user.mydentist.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.mydentist.R;

import java.util.concurrent.TimeUnit;


public class StartActivity extends AppCompatActivity {

    public final String AUTH_PREFERENS = "auth_preferens";
    public final String AUTH_TOKEN = "app_token";
    public final String AUTH_ID = "app_id";
    public String app_id = "";
    public String app_token = "";
    public boolean TrueToken = false;
    public static SharedPreferences preferences;
    //При первом запуске в преференсах AUTH_TOKEN пустой - он записывается при регистрации. При повторном запуске, если app_token = preferences.getString(AUTH_TOKEN, ""); не пустой, то доступ к главному активити.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        preferences = getSharedPreferences(AUTH_PREFERENS, Context.MODE_PRIVATE);

        app_id = preferences.getString(AUTH_ID, "");
        app_token = preferences.getString(AUTH_TOKEN, "");
        if (app_token.equals("") || app_id.equals("")) {
            TrueToken = true;
        }

            if (!TrueToken) {
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));   // использовать countdowntimer
                Intent intent = new Intent(this, HeadActivity.class);
                startActivity(intent);
                finish();
            } else {
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));   // использовать countdowntimer
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                finish();

            }
        }

    }

