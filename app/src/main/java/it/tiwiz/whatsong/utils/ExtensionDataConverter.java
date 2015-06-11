package it.tiwiz.whatsong.utils;

import android.content.Intent;

import com.google.android.apps.dashclock.api.ExtensionData;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.intents.IntentFactory;
import it.tiwiz.whatsong.settings.Settings;
import it.tiwiz.whatsong.settings.SettingsData;

/**
 * This class is a helper for converting different objects into {@link com.google.android.apps.dashclock.api.ExtensionData}
 *
 * @see #from(it.tiwiz.whatsong.settings.SettingsData) SettingsData conversion
 */
public class ExtensionDataConverter {

    /**
     * This method converts a {@link it.tiwiz.whatsong.settings.SettingsData} into a
     * {@link com.google.android.apps.dashclock.api.ExtensionData} based on rules.
     * <b>Note:</b> if the chosen provider is not installed, the default one will be used.
     *
     * @see SettingsData.Rules Rules to convert SettingsData into ExtensionData
     */
    public static ExtensionData from(SettingsData settingsData) {

        //if app is not installed, default provider is choosen
        if (!SettingsData.Rules.isChosenAppInstalled(settingsData)) {
            settingsData.resetToDefaulProvider();
        }

        int icon = SettingsData.Rules.getIconBasedOn(settingsData);

        String title = SettingsData.Rules.getTitleBasedOn(settingsData);
        String expandedBody = SettingsData.Rules.getExpandedBodyBasedOn(settingsData);
        String contentDescription = Settings.getString(R.string.content_description, settingsData.getAppTitle());
        String packageName = AppUtils.getPackageNameFrom(settingsData.getIndex());
        Intent launchProvider = IntentFactory.getLaunchIntentFor(settingsData.getIndex(), packageName);

        ExtensionData data = new ExtensionData()
                .visible(true)
                .icon(icon)
                .status(title)
                .expandedTitle(title)
                .expandedBody(expandedBody)
                .contentDescription(contentDescription)
                .clickIntent(launchProvider);

        return data;
    }

}
