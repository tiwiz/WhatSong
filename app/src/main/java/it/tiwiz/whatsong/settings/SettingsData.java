package it.tiwiz.whatsong.settings;

import android.support.annotation.NonNull;

import org.intellij.lang.annotations.MagicConstant;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.utils.AppUtils;
import it.tiwiz.whatsong.utils.IconUtils;
import it.tiwiz.whatsong.utils.IndexUtils;

/**
 * This class creates an object containing all the data gathered from the Settings
 */
public class SettingsData {

    final String defaultProvider;
    String appTitle;
    final boolean useAppSpecificIcon;
    final boolean showExtendedBody;
    final boolean useAppSpecificTitle;
    final boolean showShortTitle;
    int index;

    public static final int DASHCLOCK = 0;
    public static final int GOOGLE_NOW = 1;

    @MagicConstant(intValues = {DASHCLOCK, GOOGLE_NOW})
    @interface Provider {}

    public SettingsData(@Provider int provider) {
        defaultProvider = Settings.getDefaultMusicRecognitionProvider();
        appTitle = getCorrectNameBasedOn(provider);
        useAppSpecificIcon = Settings.getUseAppSpecificIcon();
        showExtendedBody = Settings.getShowExtendedBody();
        useAppSpecificTitle = Settings.getUseAppSpecificName();
        showShortTitle = Settings.getUseAppShortName();
        index = IndexUtils.getInternalIndexFrom(appTitle);
    }

    @NonNull
    private String getCorrectNameBasedOn(@Provider int provider) {
        String appName;

        switch (provider) {
            case DASHCLOCK:
                appName = Settings.getDashClockAppTitleBasedOnGivenProvider(defaultProvider);
                break;
            case GOOGLE_NOW:
                appName = Settings.getGoogleNowAppTitleBasedOnGiveProviderOr(defaultProvider);
                break;
            default:
                appName = "";
                break;
        }

        return appName;
    }

    public String getDefaultProvider() {
        return defaultProvider;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public boolean isUseAppSpecificIcon() {
        return useAppSpecificIcon;
    }

    public boolean isShowExtendedBody() {
        return showExtendedBody;
    }

    public boolean isUseAppSpecificTitle() {
        return useAppSpecificTitle;
    }

    public boolean isShowShortTitle() {
        return showShortTitle;
    }

    public int getIndex() {
        return index;
    }

    /**
     * This method is useful when the chosen provider is not installed and
     * the app needs to automatically set the default provider
     */
    public void resetToDefaulProvider() {
        appTitle = defaultProvider;
        index = IndexUtils.getInternalIndexFrom(defaultProvider);
    }

    public static class Rules {

        /**
         * This method will check if the chosen music recognition provider is installed or not
         * @return {@code true} if the provider is installed, {@code false} otherwise
         */
        public static boolean isChosenAppInstalled(SettingsData settingsData) {
            int chosenAppIndex = IndexUtils.getInternalIndexFrom(settingsData.getAppTitle());
            String packageName = AppUtils.getPackageNameFrom(chosenAppIndex);

            return AppUtils.isAppInstalled(packageName);
        }

        /**
         * If the user wants to see the specific provider icon, this method will return <b>that</b> app's
         * icon resource, otherwise the default WhatSong icon will be chosen.
         */
        public static int getIconBasedOn(SettingsData settingsData) {
            if (settingsData.isUseAppSpecificIcon()) {
                return IconUtils.getMusicAppIconResourceID(settingsData.getIndex());
            } else {
                return R.drawable.ic_launcher;
            }
        }

        /**
         * In case the user wants to show a small description below the extension title, the proper
         * {@link String} will be created based on the {@link SettingsData#appTitle},
         * otherwise an empty String of length 0 will be returned, hiding the description in the DashClock extension.
         */
        public static String getExpandedBodyBasedOn(SettingsData settingsData) {
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
         * @see #getTitleForSpecificAppBasedOn(SettingsData) Short VS long version
         */
        public static String getTitleBasedOn(SettingsData settingsData) {
            if (settingsData.isUseAppSpecificTitle()) {
                return getTitleForSpecificAppBasedOn(settingsData);
            } else {
                return Settings.getString(R.string.app_name);
            }
        }

        /**
         * This method will return either the long version (Shazam Encore) or the short version (Shazam) based on user's preference,
         * as defined per {@link SettingsData} parameter.
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
