package it.tiwiz.whatsong.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import it.tiwiz.whatsong.WhatSongApp;

/**
 * This class is the Base Activity containing the {@link Tracker} needed to work with Google Analytics
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = ((WhatSongApp)WhatSongApp.getInstance()).getDefaultTracker();
        tracker.setAppName("WhatSong Android");

        trackActivityBehaviour(String.format("Starting Activity: %s", getActivityTag()));
    }

    /**
     * Sends screen starting data for tracking
     */
    private void trackActivityBehaviour(String trackingMessage) {
        tracker.setScreenName(trackingMessage);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public abstract String getActivityTag();

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
