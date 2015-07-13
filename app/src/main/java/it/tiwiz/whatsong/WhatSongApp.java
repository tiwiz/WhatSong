package it.tiwiz.whatsong;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

public class WhatSongApp extends Application{

    private static Context instance;
    private Tracker tracker;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static String[] getStringArrayBy (int id) {
        return getInstance().getResources().getStringArray(id);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            tracker = analytics.newTracker(BuildConfig.ANALYTICS_USERID);
        }
        return tracker;
    }
}
