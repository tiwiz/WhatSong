package it.tiwiz.whatsong.settings;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import it.tiwiz.whatsong.R;
import it.tiwiz.whatsong.WhatSongApp;

/**
 * This class represents the main hub for the Settings in the app.
 */
public class Settings {

    /**
     * Gets the default {@link android.content.SharedPreferences} using the main app {@link android.content.Context}
     */
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(WhatSongApp.getInstance());
    }

    /**
     * @param defaultValueKey the <i>ResourceID</i> of the default boolean value
     * @return the default boolean value extracted from the preferences, {@code true} otherwise
     */
    private static boolean getDefaultBooleanValueFrom(int defaultValueKey) {
        boolean defaultValue;

        try {
            defaultValue = WhatSongApp.getInstance().getResources().getBoolean(defaultValueKey);
        } catch (Resources.NotFoundException e) {
            defaultValue = true;
        }

        return defaultValue;
    }

    /**
     * Retrieves the String representing the key for using in the {@link android.content.SharedPreferences} requests
     *
     * @param keyResource the <i>ResourceID</i> of the requested key
     * @return a {@link java.lang.String} representing the requested key
     */
    private static String getStringKeyFrom(int keyResource) {
        String key;

        try {
            key = WhatSongApp.getInstance().getResources().getString(keyResource);
        } catch (Resources.NotFoundException e) {
            key = null;
        }

        return key;
    }

    /**
     * This method uses the <b>ResourceID</b>s for retrieving both the preference key and default value instead of the usual <b>String</b>/<b>boolean</b> couple
     *
     * @return the stored value from the preferences, otherwise it will return the <i>default value</i>
     * @see #getBooleanValueFromPreferences(String, boolean)
     */
    private static boolean getBooleanValueFromPreferencesUsingResources(int preferenceKeyResource, int defaultValueKeyResource) {
        boolean defaultValue = getDefaultBooleanValueFrom(defaultValueKeyResource);
        String preferenceKey = getStringKeyFrom(preferenceKeyResource);

        return getBooleanValueFromPreferences(preferenceKey, defaultValue);
    }

    /**
     * Retrieves the requested <b>boolean</b> value with a <i>null-check</i> on the key to avoid crashes
     */
    private static boolean getBooleanValueFromPreferences(String preferenceKey, boolean defaultValue) {
        boolean preferenceValue = defaultValue;

        if (preferenceKey != null) {
            preferenceValue = getPreferences().getBoolean(preferenceKey, defaultValue);
        }

        return preferenceValue;
    }

    /**
     * Retrieves the requested <b>String</b> value with a <i>null-check</i> on the key to avoid crashes
     */
    private static String getStringValueFromPreferences(String preferenceKey, String defaultValue) {
        String preferenceValue = defaultValue;

        if (preferenceKey != null) {
            preferenceValue = getPreferences().getString(preferenceKey, defaultValue);
        }

        return preferenceValue;
    }

    /**
     * Retrieves the user's choice for the use of the specific music provider's app icon instead of the WhatSong's one
     *
     * @see #getUseAppSpecificName()
     */
    public static boolean getUseAppSpecificIcon() {
        return getBooleanValueFromPreferencesUsingResources(R.string.pref_icon_key, R.bool.useAppSpecificIcon);
    }

    /**
     * This method will get the user's preference on showing or not the extended body on DashClock notification
     */
    public static boolean getShowExtendedBody() {
        return getBooleanValueFromPreferencesUsingResources(R.string.pref_extended_body, R.bool.showExtendedBody);
    }

    /**
     * Retrieves the user's choice for the use of the specific music provider's app name instead of the WhatSong's one
     *
     * @see #getUseAppSpecificIcon()
     */
    public static boolean getUseAppSpecificName() {
        return getBooleanValueFromPreferencesUsingResources(R.string.pref_app_name, R.bool.useAppSpecificName);
    }

    /**
     * Retrieves the user's choice for the use of the shorter name instead of the full app name for each of the providers in
     * DashClock extension
     */
    public static boolean getUseAppShortName() {
        return getBooleanValueFromPreferencesUsingResources(R.string.pref_short_title, R.bool.useAppShortName);
    }

    /**
     * This method will retrieve the <b>default music recognition provider</b> based on the index value
     * specified in the <i>defaultMusicRecognitionProviderIndex</i> of the <b>defaults file</b>.
     */
    public static String getDefaultMusicRecognitionProvider() {
        final Resources resources = WhatSongApp.getInstance().getResources();
        int defaultProviderIndex = resources.getInteger(R.integer.defaultMusicRecognitionProviderIndex);

        return resources.getStringArray(R.array.softwares_names)[defaultProviderIndex];
    }

    /**
     * This method will retrieve the specific app title based on the provider
     */
    public static String getAppTitleBasedOnGivenProvider(String defaultProvider) {
        String preferenceKey = getStringKeyFrom(R.string.pref_provider_key);
        return getStringValueFromPreferences(preferenceKey, defaultProvider);
    }

    /**
     * This method is a simple wrapper of the {@link android.content.res.Resources#getString(int)} method
     */
    public static String getString(int resourceId) {
        return WhatSongApp.getInstance().getString(resourceId);
    }

    /**
     * This method is a simple wrapper of the {@link android.content.res.Resources#getString(int, Object...)} method
     */
    public static String getString(int resourceId, String... data) {
        return WhatSongApp.getInstance().getString(resourceId, data);
    }

}
