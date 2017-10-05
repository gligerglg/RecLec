package com.example.gayanlakshitha.reclec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Gayan Lakshitha on 9/16/2017.
 */

public class Splash_Screen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        SharedPreferences shared = getSharedPreferences("check", 0);
        final boolean isLogged = shared.getBoolean("login", false);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!isLogged)
                        startActivity(new Intent(Splash_Screen.this, LoginMenu.class));
                    else
                        startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                }
            }
        };

        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
