package it.tiwiz.whatsong.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import it.tiwiz.whatsong.R;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
