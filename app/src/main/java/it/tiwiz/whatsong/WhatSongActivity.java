package it.tiwiz.whatsong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import it.tiwiz.whatsong.fragments.SettingsFragment;

/**
 * This is the main Settings Activity.
 *
 * Choosing a provider here, will affect the behaviour of the DashClock Extension
 */
public class WhatSongActivity extends ActionBarActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatsong_layout);
        loadSettings();
    }

    private void loadSettings() {
        getFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .commit();
    }

}