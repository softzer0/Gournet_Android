package com.gournet.app.activity;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by SlobodanLjubic on 11/7/2017.
 */

public class GournetSplash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        new Handler().postDelayed(new Runnable() {
           @Override
            public void run() {
                Intent loginIntent=new Intent(GournetSplash.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
          }
      },SPLASH_TIME_OUT);


    }
}
