package com.gournet.app.activity;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gournet.app.model.Token;
import com.gournet.app.other.SessionManager;


/**
 * Created by SlobodanLjubic on 11/7/2017.
 */

public class  GournetSplash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           session=new SessionManager(GournetSplash.this);
            postDelayed();

    }

    public void postDelayed()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(GournetSplash.this.CONNECTIVITY_SERVICE))
                        .getActiveNetworkInfo();

                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                 //   Token token=session.getSavedTokenFromPreference(GournetSplash.this,"GournetPref","token",Token.class);
              // System.out.println(new Gson().toJson(token).toString());
                       //session.isLoggedIn();

                   //   session.checkLogin();

              Intent loginIntent = new Intent(GournetSplash.this, LoginActivity.class);
              startActivity(loginIntent);
              finish();
//                        Intent loginIntent = new Intent(GournetSplash.this, MainActivity.class);
//                        startActivity(loginIntent);
//                        finish();

                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GournetSplash.this);
                    builder.setMessage("No internet connection")
                              .setPositiveButton ("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   postDelayed();
                                }

                            });
                    AppCompatDialog alert=builder.create();
                    alert.show();



                }

           }
        },SPLASH_TIME_OUT);

    }



}





