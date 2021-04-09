package com.zoho.superhero;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Preethi on 07/04/2021.
 * Splash Screen is the first Screen visible to the user when the appliction is launched
 * A content view is set by activitysplash.xml using setContentView() Function
 * Using handle Thread hold Splash activity for 3s
 * later use Intent instance with MainActivity.class , startActivity by passing Created intent instance into it
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startIntentActivity();
    }

//The screen waits for 3 milliseconds and goes to the next activity

    private void startIntentActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}