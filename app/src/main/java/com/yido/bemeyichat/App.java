package com.yido.bemeyichat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Panch on 23.04.2016.
 */
public class App extends Application {

    public static long userID;
    public static String username;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
