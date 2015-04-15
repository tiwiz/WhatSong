package it.tiwiz.whatsong;

import android.app.Application;
import android.content.Context;

public class WhatSongApp extends Application{

    private static Context instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static String[] getStringArrayBy (int id) {
        return getInstance().getResources().getStringArray(id);
    }
}
