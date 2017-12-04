package com.gournet.app.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.gournet.app.activity.GournetSplash;
import com.gournet.app.activity.LoginActivity;
import com.gournet.app.activity.MainActivity;
import com.gournet.app.model.Token;
import com.gournet.app.model.User;

import java.util.HashMap;

/**
 * Created by SlobodanLjubic on 11/16/2017.
 */

public class SessionManager {
    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;
    //SharedPref mode
     int PRIVATE_MODE=0;


    private static final String PREF_NAME="GournetPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String REFRESH_TOKEN="refresh_token";
    public static final String FULL_NAME="full_name";
    public static final String ACCESS_TOKEN="access_token";
    public static final String USERNAME="username";
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";

    public static final String TOKEN_OBJECT="token";
    Token token;

    //public static String DATE_REFRESH_TOKEN= "date_refresh_token";
   // public static String DATE_ACCESS_TOKEN= "date_access_token";


    public SessionManager(Context context)
    {
        this._context=context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void createLoginSession(String refresh_token,String access_token,String username,String full_name,Double latitude,Double longitude)
    {
       // ,Long date_refresh_token,Long date_access_token
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(REFRESH_TOKEN,refresh_token);
        editor.putString(ACCESS_TOKEN,access_token);
        editor.putString(USERNAME,username);
        editor.putString(FULL_NAME,full_name);
         putDouble(editor,LATITUDE,latitude);
         putDouble(editor,LONGITUDE,longitude);

       // editor.putLong( DATE_REFRESH_TOKEN,date_refresh_token);
      //  editor.putLong(DATE_ACCESS_TOKEN,date_access_token);
        this.editor.commit();
    }


    public HashMap<String,String> getSessionData()
    {
        HashMap<String,String> sessionData=new HashMap<String,String>();
        sessionData.put(REFRESH_TOKEN,pref.getString(REFRESH_TOKEN,null));
        sessionData.put(ACCESS_TOKEN,pref.getString(ACCESS_TOKEN,null));
        sessionData.put(USERNAME, pref.getString(USERNAME, null));
        sessionData.put(FULL_NAME,pref.getString(FULL_NAME,null));

       // sessionData.put(DATE_REFRESH_TOKEN,pref.getString(DATE_REFRESH_TOKEN, null));
        //sessionData.put(DATE_ACCESS_TOKEN,pref.getString(DATE_ACCESS_TOKEN,null));

        return sessionData;
    }
    public HashMap<String,Double> getLocation()
    {
        HashMap<String,Double> sessionData=new HashMap<String,Double>();
        sessionData.put(LATITUDE,getDouble(pref,LATITUDE,0D));
        sessionData.put(LONGITUDE,getDouble(pref,LONGITUDE,0D));

        return getLocation();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
        else
        {
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            // i.putExtra("user", getToken().getUser());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public static void saveTokenToPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

//    public Token  getTokenDataFromPreferences() {
//
//        SharedPreferences sharedPreferences = this._context.getSharedPreferences("GournetPref", 0);
//        Gson gson = new Gson();
//        gson.fromJson(sharedPreferences.getString("token", ""),Token.class);
//        return token;
//
//    }
//    public User getUserDataFromPreferences()
//    {
//        SharedPreferences sharedPreferences = this._context.getSharedPreferences("GournetPref", 0);
//        Gson gson = new Gson();
//        gson.fromJson(sharedPreferences.getString("user", ""), User.class);
//        return user;
//    }

    public static <GenericClass> GenericClass getSavedTokenFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }



     public static  SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    public static  double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}

//sacuvaj ceo objekat Token u SharedPreferences
//napisi metode koje deseralizuju Tak token objekat i vracaju User data Refresh i Access