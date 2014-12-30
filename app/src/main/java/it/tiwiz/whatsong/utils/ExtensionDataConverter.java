package it.tiwiz.whatsong.utils;

import android.content.Intent;

import com.google.android.apps.dashclock.api.ExtensionData;

import it.tiwiz.whatsong.R;
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
     * @see it.tiwiz.whatsong.utils.ExtensionDataConverter.SettingsDataRules Rules to convert SettingsData into ExtensionData
     */
    public static ExtensionData from(SettingsData settingsData) {

        //if app is not installed, default provider is choosen
        if (!SettingsDataRules.isChosenAppInstalled(settingsData)) {
            settingsData.resetToDefaulProvider();
        }

        int icon = SettingsDataRules.getIconBasedOn(settingsData);

        String title = SettingsDataRules.getTitleBasedOn(settingsData);
        String expandedBody = SettingsDataRules.getExpandedBodyBasedOn(settingsData);
        String contentDescription = Settings.getString(R.string.content_description, settingsData.getAppTitle());
        String packageName = AppUtils.getPackageNameFrom(settingsData.getIndex());
        Intent launchProvider = IntentUtils.getLaunchIntent(settingsData.getIndex(), packageName);

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

    private static class SettingsDataRules {

        /**
         * This method will check if the chosen music recognition provider is installed or not
         * @return {@code true} if the provider is installed, {@code false} otherwise
         */
        protected static boolean isChosenAppInstalled(SettingsData settingsData) {
            int chosenAppIndex = IndexUtils.getInternalIndexFrom(settingsData.getAppTitle());
            String packageName = AppUtils.getPackageNameFrom(chosenAppIndex);

            return AppUtils.isAppInstalled(packageName);
        }

        /**
         * If the user wants to see the specific provider icon, this method will return <b>that</b> app's
         * icon resource, otherwise the default WhatSong icon will be chosen.
         */
        protected static int getIconBasedOn(SettingsData settingsData) {
            if (settingsData.isUseAppSpecificIcon()) {
                return IconUtils.getMusicAppIconResourceID(settingsData.getIndex());
            } else {
                return R.drawable.ic_launcher;
            }
        }

        /**
         * In case the user wants to show a small description below the extension title, the proper
         * {@link java.lang.String} will be created based on the {@link it.tiwiz.whatsong.settings.SettingsData#appTitle},
         * otherwise an empty String of length 0 will be returned, hiding the description in the DashClock extension.
         */
        protected static String getExpandedBodyBasedOn(SettingsData settingsData) {
            if (settingsData.isShowExtendedBody()) {
                return Settings.getString(R.string.expanded_body, settingsData.getAppTitle());
            } else {
                return "";
            }
        }

        /**
         * If the user prefers to see the specific provider name, he/she will then choose between the long
         * or the short version for the app name (such as Shazam Encore VS Shazam), otherwise, it will return
         * the "WhatSong" title.
         *
         * @see #getTitleForSpecificAppBasedOn(it.tiwiz.whatsong.settings.SettingsData) Short VS long version
         */
        protected static String getTitleBasedOn(SettingsData settingsData) {
            if (settingsData.isUseAppSpecificTitle()) {
                return getTitleForSpecificAppBasedOn(settingsData);
            } else {
                return Settings.getString(R.string.app_name);
            }
        }

        /**
         * This method will return either the long version (Shazam Encore) or the short version (Shazam) based on user's preference,
         * as defined per {@link it.tiwiz.whatsong.settings.SettingsData} parameter.
         */
        private static String getTitleForSpecificAppBasedOn(SettingsData settingsData) {
            if (settingsData.isShowShortTitle()) {
                return AppUtils.getShortTitleFrom(settingsData.getIndex());
            } else {
                return AppUtils.getTitleFrom(settingsData.getIndex());
            }
        }

    }
}
