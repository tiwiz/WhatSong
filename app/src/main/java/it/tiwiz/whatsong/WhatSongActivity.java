package it.tiwiz.whatsong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.tiwiz.whatsong.fragments.SettingsFragment;
import it.tiwiz.whatsong.utils.BaseActivity;

/**
 * This is the main Settings Activity.
 *
 * Choosing a provider here, will affect the behaviour of the DashClock Extension
 */
public class WhatSongActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatsong_layout);
        loadSettings();
    }

    @Override
    public String getActivityTag() {
        return WhatSongActivity.class.getSimpleName();
    }

    private void loadSettings() {
        getFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment())
                .commit();
    }

}