package it.tiwiz.whatsong;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import it.tiwiz.whatsong.settings.SettingsData;
import it.tiwiz.whatsong.utils.ExtensionDataConverter;

public class WhatSongExtension extends DashClockExtension {
    @Override
    protected void onUpdateData(int i) {

        SettingsData settingsData = new SettingsData();

        ExtensionData data = ExtensionDataConverter.from(settingsData);

        publishUpdate(data);
    }

}
